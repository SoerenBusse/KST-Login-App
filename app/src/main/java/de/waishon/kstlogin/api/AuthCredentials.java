package de.waishon.kstlogin.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import de.waishon.kstlogin.utils.SchoolSave;

/**
 * Created by Sören on 18.02.2017.
 */

public class AuthCredentials {
    private String username;
    private String password;

    public AuthCredentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void save(Context context) {
        if (SchoolSave.IS_SCHOOL_VERSION) {
            SchoolSave.username = this.username;
            SchoolSave.password = this.password;
            return;
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("username", this.username);
        editor.putString("password", this.password);

        editor.commit();
    }

    public static AuthCredentials load(Context context) {
        if (SchoolSave.IS_SCHOOL_VERSION) {
            return new AuthCredentials(SchoolSave.username, SchoolSave.password);
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");

        return new AuthCredentials(username, password);
    }

    public static boolean delete(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.edit().remove("username").remove("password").commit();
    }

    public static boolean exists(Context context) {
        if (SchoolSave.IS_SCHOOL_VERSION) {
            return (!SchoolSave.username.equals("") && !SchoolSave.password.equals(""));
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return (sharedPreferences.contains("username") && sharedPreferences.contains("password") &&
                (!sharedPreferences.getString("username", "").equals("") && !sharedPreferences.getString("password", "").equals("")));
    }
}
