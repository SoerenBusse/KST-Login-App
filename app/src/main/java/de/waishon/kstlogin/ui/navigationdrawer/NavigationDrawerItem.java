package de.waishon.kstlogin.ui.navigationdrawer;

import android.support.v4.app.Fragment;

/**
 * Created by Sören on 10.04.2016.
 */
public class NavigationDrawerItem {

    private String entryName;
    private int image;
    private Class fragment;

    public <T extends Fragment> NavigationDrawerItem(String entryName, int image, Class<T> fragment) {
        this.entryName = entryName;
        this.image = image;
        this.fragment = fragment;
    }

    public String getEntryName() {
        return entryName;
    }

    public int getImage() {
        return image;
    }

    public Class getFragmentClass() {
        return fragment;
    }
}
