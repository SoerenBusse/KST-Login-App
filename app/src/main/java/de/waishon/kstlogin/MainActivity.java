package de.waishon.kstlogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import de.waishon.kstlogin.api.ApiError;
import de.waishon.kstlogin.api.ApiMethods;
import de.waishon.kstlogin.api.ApiResponse;
import de.waishon.kstlogin.api.AuthCredentials;
import de.waishon.kstlogin.fragments.VMListFragment;
import de.waishon.kstlogin.ui.navigationdrawer.NavigationDrawer;

public class MainActivity extends AppCompatActivity {

    public static final String PREF_FIRST_RUN = "firstRun";
    // UI
    private Toolbar toolbar;
    private NavigationDrawer navigationDrawer;
    private MaterialDialog materialDialog;
    private TextView loggedInAsTextview;

    // API
    private ApiMethods apiMethods;

    // System
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // System
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        // Api Methods initialisieren
        apiMethods = new ApiMethods(getApplicationContext());

        // Sind überhaupt Logindaten vorhanden?
        if (!AuthCredentials.exists(getApplicationContext())) {
            redirectToLoginPage();
            return;
        }

        if (!sharedPreferences.contains(PREF_FIRST_RUN) && sharedPreferences.getBoolean(PREF_FIRST_RUN, true)) {
            startActivity(new Intent(MainActivity.this, IntroActivity.class));
            finish();
            return;
        }

        if (!getIntent().hasExtra("login")) {
            // Prüfen, ob die gespeicherten Logindaten korrekt sind?
            apiMethods.checkAuthorization(AuthCredentials.load(getApplicationContext()), new ApiResponse() {
                @Override
                public void onError(ApiError apiError) {
                    materialDialog.dismiss();
                    redirectToLoginPage();
                }

                @Override
                public void onFinish(JSONObject response) {
                    // VM-Liste beim Start anzeigen
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_main_frame, new VMListFragment()).commitAllowingStateLoss();

                    materialDialog.dismiss();
                }

                @Override
                public void onStart() {
                    materialDialog = new MaterialDialog.Builder(MainActivity.this)
                            .content(R.string.acitivty_main_check_authentification)
                            .progress(true, 0)
                            .cancelable(false)
                            .show();
                }
            });
        } else {
            // Wenn der User von der Login-Seite kommt -> direkt anzeigen
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.activity_main_frame, new VMListFragment()).commitAllowingStateLoss();
        }

        // UI initialisieren
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.loggedInAsTextview = (TextView) findViewById(R.id.activity_main_drawer_loggedin_as);

        // User setzen
        this.loggedInAsTextview.setText(AuthCredentials.load(getApplicationContext()).getUsername());

        // Toolbar setzen
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        this.navigationDrawer = new NavigationDrawer(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        apiMethods.cancelAll();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_main_logout:
                AuthCredentials.delete(this);
                redirectToLoginPage();
                return true;
            case R.id.menu_main_settings:
                startActivity(new Intent(MainActivity.this, SettingsActivity.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public Toolbar getToolbar() {
        return this.toolbar;
    }

    public void redirectToLoginPage() {
        startActivity(new Intent(MainActivity.this, LoginActivity.class));
        finish();
    }
}
