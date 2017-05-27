package de.waishon.kstlogin.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Point;
import android.os.Build;
import android.util.Log;
import android.view.Display;
import android.view.WindowManager;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Sören on 09.04.2017.
 */

public class ResolutionManager {

    ArrayList<Resolution> availableResolutionList = new ArrayList<>();

    private Context context;
    private WindowManager windowManager;

    public ResolutionManager(Context context) {
        this.context = context;
        this.windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);

        initializeResolutions();
    }

    private void initializeResolutions() {
        float scale = getScale();

        Log.i("KST", "" + scale);

        // 0x0 -> Automatisch
        availableResolutionList.add(new Resolution(0, scale));

        // Anderen Auflösungen
        availableResolutionList.add(new Resolution(2560, scale));
        availableResolutionList.add(new Resolution(2048, scale));
        availableResolutionList.add(new Resolution(1920, scale));
        availableResolutionList.add(new Resolution(1600, scale));
        availableResolutionList.add(new Resolution(1366, scale));
        availableResolutionList.add(new Resolution(1280, scale));
        availableResolutionList.add(new Resolution(1024, scale));
        availableResolutionList.add(new Resolution(960, scale));

        Log.i("KST", Arrays.toString(availableResolutionList.toArray()));
    }

    public ArrayList<Resolution> getAvailableResolutionList() {
        return availableResolutionList;
    }

    public String[] getAvailableResolutionArray() {
        String[] availableResolutions = new String[availableResolutionList.size()];

        for (int i = 0; i < availableResolutionList.size(); i++) {
            availableResolutions[i] = availableResolutionList.get(i).toString();
        }

        return availableResolutions;
    }

    public Resolution getResolutionByString(String resolutionString) {
        for (Resolution resolution : availableResolutionList) {
            if (resolution.toString().equals(resolutionString)) {
                return resolution;
            }
        }

        return availableResolutionList.get(0);
    }

    // Verhältnis ermitteln
    private float getScale() {
        Display display = windowManager.getDefaultDisplay();

        int width = 0;
        int height = 0;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            Point size = new Point();
            display.getRealSize(size);

            width = size.y;
            height = size.x;
        } else {
            try {
                Method mGetRawH = Display.class.getMethod("getRawHeight");
                Method mGetRawW = Display.class.getMethod("getRawWidth");

                width = (Integer) mGetRawW.invoke(display);
                height = (Integer) mGetRawH.invoke(display);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            int tempWidth = width;
            width = height;
            height = tempWidth;
        }

        Log.i("KST", "HEIGHT" + height + " WIDHT" + width + "(height / width)" + (height / width));

        return ((float) height / (float) width);
    }


}
