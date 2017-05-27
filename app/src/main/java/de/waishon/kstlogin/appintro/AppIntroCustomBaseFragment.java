package de.waishon.kstlogin.appintro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;

/**
 * Created by Sören on 11.03.2017.
 */

public class AppIntroCustomBaseFragment extends Fragment implements ISlideBackgroundColorHolder {

    // Bundle Arguments
    private static final String ARG_BACKGROUND_COLOR = "backgroundColor";

    // Konstruktionsparameter
    private int backgroundColor = 0;

    public static <T extends AppIntroCustomBaseFragment> T newInstance(Class<T> type, int backgroundColor) {
        try {
            // Neue Instanz erstellen
            T fragment = type.newInstance();

            // Parameter übergeben
            Bundle args = new Bundle();
            args.putInt(ARG_BACKGROUND_COLOR, backgroundColor);
            fragment.setArguments(args);

            // Neues Fragment zurückgeben
            return fragment;

            // BÖSE!
        } catch (java.lang.InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        // Irgendetwas ist wohl schief gelaufen?!
        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().size() != 0) {
            backgroundColor = getArguments().getInt(ARG_BACKGROUND_COLOR);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            backgroundColor = savedInstanceState.getInt(ARG_BACKGROUND_COLOR);
        }
    }


    @Override
    public int getDefaultBackgroundColor() {
        return backgroundColor;
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        getView().setBackgroundColor(backgroundColor);
    }
}
