package de.waishon.kstlogin.appintro;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.github.paolorotolo.appintro.ISlidePolicy;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.listener.OnWindowFocusListener;
import de.waishon.kstlogin.utils.Utils;

/**
 * Created by Sören on 11.03.2017.
 */

public class AppIntroKeyBoardFragment extends AppIntroCustomBaseFragment implements OnWindowFocusListener, ISlidePolicy {

    private static final String KEYBOARD_PACKAGE = "org.pocketworkstation.pckeyboard";

    // UI
    private TextView descriptionTextView;
    private Button actionButton;
    private CheckBox checkBoxDontInstall;

    // System Services
    private InputMethodManager inputMethodManager;

    // Input Picker
    private int picking = 0;

    // Status
    private boolean finish = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.appintro_fragment_keyboard, container, false);

        // UI initialisieren
        descriptionTextView = (TextView) view.findViewById(R.id.appintro_fragment_keyboard_description);
        actionButton = (Button) view.findViewById(R.id.appintro_fragment_keyboard_action_button);
        checkBoxDontInstall = (CheckBox) view.findViewById(R.id.appintro_fragment_keyboard_dont_install_checkbox);

        // System Services initialisieren
        inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);

        changeStatus();

        view.setBackgroundColor(super.getDefaultBackgroundColor());
        return view;
    }

    public void changeStatus() {
        // Wenn noch nicht fertig, alles anzeigen
        if (!finish) {
            actionButton.setVisibility(View.VISIBLE);
            checkBoxDontInstall.setVisibility(View.VISIBLE);
        }

        // Ist "Hackers-Keyboard" nicht installiert
        if (!Utils.isPackageExisted(getContext(), KEYBOARD_PACKAGE)) {
            descriptionTextView.setText(getString(R.string.appintro_fragment_keyboard_install_description));
            actionButton.setText(getString(R.string.appintro_fragment_keyboard_install_button));

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Playstore öffnen
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + KEYBOARD_PACKAGE));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    } catch (android.content.ActivityNotFoundException anfe) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + KEYBOARD_PACKAGE));
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                }
            });

            finish = false;
            return;
        }

        if (!Utils.isKeyboardActivated(inputMethodManager, KEYBOARD_PACKAGE)) {
            descriptionTextView.setText(getString(R.string.appintro_fragment_keyboard_setup_description));
            actionButton.setText(getString(R.string.appintro_fragment_keyboard_setup_button));

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent settingsIntent = new Intent(Settings.ACTION_INPUT_METHOD_SETTINGS);
                    startActivity(settingsIntent);
                }
            });

            finish = false;
            return;
        }

        if (!Utils.isKeyboardCurrentKeyboard(getContext(), KEYBOARD_PACKAGE)) {
            String description = getString(R.string.appintro_fragment_keyboard_setdefault_description);
            description = description.replace("__currentkeyboard__", Utils.getDefaultKeyboardName(getContext(), inputMethodManager));

            descriptionTextView.setText(description);
            actionButton.setText(getString(R.string.appintro_fragment_keyboard_setdefault_button));

            actionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inputMethodManager.showInputMethodPicker();
                    picking = 1;
                }
            });

            finish = false;
            return;
        }

        descriptionTextView.setText(getString(R.string.appintro_fragment_keyboard_finish_description));
        actionButton.setVisibility(View.INVISIBLE);
        checkBoxDontInstall.setVisibility(View.GONE);

        finish = true;
    }


    @Override
    public void onFocusChanged() {
        if (picking == 1) {
            picking = 2;
        } else if (picking == 2) {
            picking = 0;
            // Update UI
            changeStatus();
        }

    }

    @Override
    public void onResume() {
        super.onResume();

        changeStatus();
    }

    @Override
    public boolean isPolicyRespected() {
        return finish || checkBoxDontInstall.isChecked();
    }

    @Override
    public void onUserIllegallyRequestedNextPage() {

    }
}
