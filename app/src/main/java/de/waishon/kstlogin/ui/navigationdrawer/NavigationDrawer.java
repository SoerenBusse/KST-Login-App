package de.waishon.kstlogin.ui.navigationdrawer;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;

import de.waishon.kstlogin.MainActivity;
import de.waishon.kstlogin.R;
import de.waishon.kstlogin.fragments.MTSWebFragment;
import de.waishon.kstlogin.fragments.VMListFragment;

/**
 * Created by Sören on 10.04.2016.
 */
public class NavigationDrawer {

    // Verwaltet die Liste mit den Items
    private NavigationDrawerAdapter navigationDrawerAdapter;

    // Recyclerview mit den Items
    private RecyclerView recyclerView;

    // Drawerlayout
    private DrawerLayout drawerLayout;

    // AppCompatActivity
    private AppCompatActivity appCompatActivity;

    public NavigationDrawer(AppCompatActivity appCompatActivity) {
        this.appCompatActivity = appCompatActivity;

        // RecyclerView einrichten
        recyclerView = (RecyclerView) appCompatActivity.findViewById(R.id.activity_main_recyclerview);
        recyclerView.setHasFixedSize(true);

        // Layoutmanager für den RecyclerView setzen
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(appCompatActivity);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // Drawerlayout zuweisen
        drawerLayout = (DrawerLayout) appCompatActivity.findViewById(R.id.activity_main_drawer_layout);

        // Neue Items Liste erstellen
        navigationDrawerAdapter = new NavigationDrawerAdapter(appCompatActivity, drawerLayout, new ArrayList<>(Arrays.asList(
                new NavigationDrawerItem(appCompatActivity.getResources().getString(R.string.drawer_vmlist), R.drawable.ic_vmlist, VMListFragment.class),
                /*new NavigationDrawerItem(appCompatActivity.getResources().getString(R.string.drawer_mtsweb), R.drawable.ic_mtsweb, MTSWebFragment.class),*/
                new NavigationDrawerItem(appCompatActivity.getResources().getString(R.string.drawer_changekeyboard), R.drawable.ic_keyboard_drawer, null)
        )));

        // Adapter setzen
        recyclerView.setAdapter(navigationDrawerAdapter);

        NavigationDrawerToggle navigationDrawerToggle = new NavigationDrawerToggle();
        navigationDrawerToggle.syncState();

        drawerLayout.addDrawerListener(navigationDrawerToggle);
    }

    class NavigationDrawerToggle extends ActionBarDrawerToggle {

        public NavigationDrawerToggle() {
            super(appCompatActivity, drawerLayout, ((MainActivity) appCompatActivity).getToolbar(), R.string.open_drawer, R.string.close_drawer);
        }

        @Override
        public void onDrawerOpened(View drawerView) {
            recyclerView.bringToFront();
            drawerLayout.requestLayout();
        }
    }

    public void update() {
        recyclerView.setAdapter(navigationDrawerAdapter);
    }
}
