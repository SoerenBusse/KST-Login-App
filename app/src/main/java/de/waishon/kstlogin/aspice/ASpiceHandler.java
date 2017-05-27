package de.waishon.kstlogin.aspice;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import com.iiordanov.bVNC.ConnectionBean;
import com.iiordanov.bVNC.Constants;
import com.iiordanov.bVNC.RemoteCanvasActivity;

import de.waishon.kstlogin.R;
import de.waishon.kstlogin.listener.OnASpiceStartListener;
import de.waishon.kstlogin.ui.vmlist.VmListItem;
import de.waishon.kstlogin.utils.Resolution;
import de.waishon.kstlogin.utils.ResolutionManager;

/**
 * Created by Sören on 20.02.2017.
 */

public class ASpiceHandler {

    private static final String PARAM_SPICE_PWD = "SpicePassword";

    public static void startASpice(Context context, VmListItem vmListItem, OnASpiceStartListener onASpiceStartListener) {
        // Verbindungsinformationen erstellen
        ConnectionBean connectionBean = new ConnectionBean(context);

        connectionBean.setAddress(vmListItem.getIp());
        connectionBean.setPassword(vmListItem.getPassword());
        connectionBean.setLayoutMap("German (Germany)");
        connectionBean.setPort(vmListItem.getPort());
        connectionBean.setEnableSound(false);
        connectionBean.setEnableRecording(false);
        connectionBean.setScaleMode(ImageView.ScaleType.MATRIX);

        // Auflösung aus Konfig laden
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        if (sharedPreferences.contains(context.getResources().getString(R.string.preference_resolution_key))) {
            String resolutionString = sharedPreferences.getString(context.getResources().getString(R.string.preference_resolution_key), context.getResources().getString(R.string.preference_resolution_defaultValue));

            if (!resolutionString.equals(context.getResources().getString(R.string.preference_resolution_defaultValue))) {
                Resolution resolution = new ResolutionManager(context).getResolutionByString(resolutionString);

                connectionBean.setRdpResType(Constants.RDP_GEOM_SELECT_CUSTOM);
                connectionBean.setRdpWidth(resolution.getWidth());
                connectionBean.setRdpHeight(resolution.getHeight());
            }
        }

        Intent intent = new Intent(context, RemoteCanvasActivity.class);
        intent.putExtra(Constants.CONNECTION, connectionBean.Gen_getValues());
        context.startActivity(intent);
    }

}
