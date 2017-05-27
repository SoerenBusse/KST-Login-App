package de.waishon.kstlogin.appintro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.paolorotolo.appintro.ISlideBackgroundColorHolder;
import com.github.paolorotolo.appintro.ISlidePolicy;

import de.waishon.kstlogin.MainActivity;
import de.waishon.kstlogin.R;
import de.waishon.kstlogin.aspice.ASpiceHandler;
import de.waishon.kstlogin.utils.Utils;

/**
 * Created by Sören on 10.03.2017.
 */

public class AppIntroASpiceFragment extends AppIntroCustomBaseFragment implements ISlidePolicy {

    // UI
    private Button downloadButton;
    private TextView notAllowedTextView;
    private LinearLayout downloadLinearLayout;
    private LinearLayout alreadyInstalledLinearLayout;

    // aSpice
    private boolean aSpiceAlreadyInstalled = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appintro_fragment_aspice, container, false);

        // UI initialisieren
        downloadButton = (Button) view.findViewById(R.id.appintro_fragment_aspice_download_button);
        notAllowedTextView = (TextView) view.findViewById(R.id.appintro_fragment_aspice_not_allowed);
        downloadLinearLayout = (LinearLayout) view.findViewById(R.id.appintro_fragment_aspice_download_container);
        alreadyInstalledLinearLayout = (LinearLayout) view.findViewById(R.id.appintro_fragment_aspice_already_installed_container);

        checkForASpice();

        // Download Button -> Playstore öffnen
        downloadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aSpicePackageName = "com.iiordanov.freeaSPICE";

                // Playstore öffnen
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + aSpicePackageName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (android.content.ActivityNotFoundException anfe) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + aSpicePackageName));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });


        view.setBackgroundColor(super.getDefaultBackgroundColor());
        return view;
    }

    public void checkForASpice() {
        // ASpice überprüfen
        /*if(ASpiceHandler.getSpicePackage(getContext()) != null) {
            aSpiceAlreadyInstalled = true;
            downloadLinearLayout.setVisibility(View.GONE);
            alreadyInstalledLinearLayout.setVisibility(View.VISIBLE);
        } else {
            aSpiceAlreadyInstalled = false;
            downloadLinearLayout.setVisibility(View.VISIBLE);
            alreadyInstalledLinearLayout.setVisibility(View.GONE);
        }*/
    }

    @Override
    public boolean isPolicyRespected() {
        return aSpiceAlreadyInstalled;
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {
        notAllowedTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();

        checkForASpice();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

}
