package de.waishon.kstlogin.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.view.inputmethod.InputMethodInfo;
import android.view.inputmethod.InputMethodManager;

import java.util.List;

/**
 * Created by Sören on 10.03.2017.
 */

public class Utils {
    public static boolean isPackageExisted(Context context, String targetPackage) {
        PackageManager pm = context.getPackageManager();
        List<ApplicationInfo> packages = pm.getInstalledApplications(0);

        for (ApplicationInfo packageInfo : packages) {
            if (packageInfo.packageName.equals(targetPackage))
                return true;
        }

        return false;
    }

    public static boolean isKeyboardActivated(InputMethodManager inputMethodManager, String packageName) {
        List<InputMethodInfo> inputMethodInfos = inputMethodManager.getEnabledInputMethodList();

        for (InputMethodInfo inputMethodInfo : inputMethodInfos) {
            if (inputMethodInfo.getPackageName().equals(packageName)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isKeyboardCurrentKeyboard(Context context, String packageName) {
        return Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD).contains(packageName);
    }

    public static String getDefaultKeyboardName(Context context, InputMethodManager inputMethodManager) {
        String defaultKeyboard =  Settings.Secure.getString(context.getContentResolver(), Settings.Secure.DEFAULT_INPUT_METHOD);
        List<InputMethodInfo> inputMethodInfos = inputMethodManager.getEnabledInputMethodList();

        for (InputMethodInfo inputMethodInfo : inputMethodInfos) {
            if (defaultKeyboard.contains(inputMethodInfo.getPackageName())) {
                return inputMethodInfo.loadLabel(context.getPackageManager()).toString();
            }
        }

        return "N/A";
    }
}
