package de.waishon.kstlogin.ui.navigationdrawer;


import android.content.Context;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import java.util.ArrayList;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 10.04.2016.
 */
public class NavigationDrawerAdapter extends RecyclerView.Adapter<NavigationDrawerViewHolder> {

    // Enthält die Items
    private ArrayList<NavigationDrawerItem> navItems;

    // Application-Context
    private AppCompatActivity appCompatActivity;

    // DrawerLayout
    private DrawerLayout drawerLayout;

    /**
     * Konstruktor
     *
     * @param navItems Die Elemente die angezeigt werden sollen
     */
    public NavigationDrawerAdapter(AppCompatActivity appCompatActivity, DrawerLayout drawerLayout, ArrayList<NavigationDrawerItem> navItems) {
        this.drawerLayout = drawerLayout;
        this.appCompatActivity = appCompatActivity;
        this.navItems = navItems;
    }

    @Override
    public NavigationDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_navigationdrawer, parent, false);

        return new NavigationDrawerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final NavigationDrawerViewHolder holder, int position) {
        final NavigationDrawerItem navItem = navItems.get(position);
        holder.getEntryNameTextView().setText(navItem.getEntryName());
        holder.getImageView().setImageResource(navItem.getImage());

        holder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Sonderbehandlung für einfache Aktionen
                if (navItem.getFragmentClass() == null && navItem.getImage() == R.drawable.ic_keyboard_drawer) {
                    InputMethodManager inputMethodManager = (InputMethodManager) appCompatActivity.getSystemService(Context.INPUT_METHOD_SERVICE);
                    inputMethodManager.showInputMethodPicker();
                } else {
                    FragmentTransaction transaction = appCompatActivity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.activity_main_frame, Fragment.instantiate(appCompatActivity, navItem.getFragmentClass().getName())).commitAllowingStateLoss();
                }

                drawerLayout.closeDrawers();
            }
        });
    }

    @Override
    public int getItemCount() {
        return navItems.size();
    }

}
