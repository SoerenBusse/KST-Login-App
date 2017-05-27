package de.waishon.kstlogin.api;

import android.content.Context;
import android.util.Log;
import android.util.SparseIntArray;

import de.waishon.kstlogin.R;

/**
 * Created by Sören on 18.02.2017.
 */

public class ApiError {

    public enum Code {
        NETWORK_CONNECTION_PROBLEM,
        JSON_PARSING,
        UNKNOWN,
    }

    // The Codes
    private static SparseIntArray codeTable = new SparseIntArray();

    static {
        // Initilize Codes
        // General
        codeTable.append(Code.NETWORK_CONNECTION_PROBLEM.ordinal(), R.string.error_connection_problem);
        codeTable.append(Code.JSON_PARSING.ordinal(), R.string.error_json_parsing);
        codeTable.append(Code.UNKNOWN.ordinal(), R.string.error_unknown);

        // Login
        codeTable.append(400002, R.string.error_invalid_arguments);
        codeTable.append(401003, R.string.error_invalid_credentials);
        codeTable.append(401004, R.string.error_invalid_credentials);
        codeTable.append(400005, R.string.error_invalid_credentials);

        // Server Realms
        // NO ERRORS HERE

        // List VMs
        codeTable.append(400032, R.string.error_invalid_arguments);
        codeTable.append(400033, R.string.error_not_enough_permission);

        // Assign VM
        codeTable.append(400042, R.string.error_invalid_arguments);
        codeTable.append(500043, R.string.error_no_free_vms);
    }

    private int code;
    private Context context;

    public ApiError(Context context, int code) {
        this.context = context;
        this.code = code;
    }

    public ApiError(Context context, Code code) {
        this.context = context;
        this.code = code.ordinal();
    }

    public String getMessage() {
        Log.d("ApiError.getMessage", "Code: " + code);

        return context.getResources().getString(codeTable.get(code, codeTable.get(Code.UNKNOWN.ordinal())));
    }

    public Context getContext() {
        return context;
    }
}
