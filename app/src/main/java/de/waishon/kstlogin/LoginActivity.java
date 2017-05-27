package de.waishon.kstlogin;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;

import org.json.JSONObject;

import de.waishon.kstlogin.api.ApiError;
import de.waishon.kstlogin.api.ApiMethods;
import de.waishon.kstlogin.api.ApiResponse;
import de.waishon.kstlogin.api.AuthCredentials;

public class LoginActivity extends AppCompatActivity {

    // UI
    private CoordinatorLayout coordinatorLayout;
    private EditText username;
    private EditText password;
    private Button loginButton;

    // API
    private ApiMethods apiMethods;

    // API-UI
    private MaterialDialog materialDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Layout initialisieren
        this.coordinatorLayout = (CoordinatorLayout) findViewById(R.id.activity_login_coordinator_layout);
        this.username = (EditText) findViewById(R.id.activity_login_username);
        this.password = (EditText) findViewById(R.id.activity_login_password);
        this.loginButton = (Button) findViewById(R.id.activity_login_button);

        // API initialisieren
        this.apiMethods = new ApiMethods(getApplicationContext());

        // Login Button Klick-handeln
        this.loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkAuthorization();
            }
        });
    }

    public void checkAuthorization() {
        final AuthCredentials authCredentials = new AuthCredentials(this.username.getText().toString(), this.password.getText().toString());

        apiMethods.checkAuthorization(authCredentials, new ApiResponse() {
            @Override
            public void onError(ApiError apiError) {
                materialDialog.dismiss();
                Snackbar.make(coordinatorLayout, apiError.getMessage(), Snackbar.LENGTH_LONG).show();
            }

            @Override
            public void onFinish(JSONObject response) {
                materialDialog.dismiss();

                // Die Zugansdaten speichern
                authCredentials.save(getApplicationContext());

                // Main Activity starten
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                intent.putExtra("login", true);
                startActivity(intent);
                finish();
            }

            @Override
            public void onStart() {
                materialDialog = new MaterialDialog.Builder(LoginActivity.this)
                        .content(R.string.login_wait_authentification)
                        .progress(true, 0)
                        .cancelable(false)
                        .show();
            }
        });
    }
}
