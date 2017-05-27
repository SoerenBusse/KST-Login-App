package de.waishon.kstlogin.fragments;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.utils.ResolutionManager;

/**
 * Created by Sören on 09.04.2017.
 */

public class SettingsFragment extends PreferenceFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);

        ResolutionManager resolutionManager = new ResolutionManager(getActivity().getApplicationContext());

        ListPreference listPreference = (ListPreference) findPreference(getActivity().getApplicationContext().getResources().getString(R.string.preference_resolution_key));
        listPreference.setEntryValues(resolutionManager.getAvailableResolutionArray());
        listPreference.setEntries(resolutionManager.getAvailableResolutionArray());
    }


}
