package de.waishon.kstlogin.ui.vmlist;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.widget.TextView;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 20.02.2017.
 */

public class StatusDesigner {

    public enum Status {
        online("Gestartet", R.color.colorStatusOnline),
        offline("Gestoppt", R.color.colorStatusOffline),
        assignable("Verfügbar", R.color.colorStatusAssignable),
        assigned("Zugewiesen", R.color.colorStatusAssigned);

        String name;
        int colorCode;

        Status(String name, int colorStatus) {
            this.name = name;
            this.colorCode = colorStatus;
        }

        public int getColorCode() {
            return this.colorCode;
        }

        public String getName() {
            return name;
        }
    }

    private Context context;

    public StatusDesigner(Context context) {
        this.context = context;
    }

    public TextView design(TextView textView, Status status) {
        textView.setBackgroundColor(ContextCompat.getColor(context, status.getColorCode()));
        textView.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
        textView.setTypeface(null, Typeface.BOLD);
        textView.setText(status.getName());

        return textView;
    }
}
