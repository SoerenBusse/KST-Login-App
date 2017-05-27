package de.waishon.kstlogin.api;

import android.content.Context;

/**
 * Created by Sören on 27.05.2017.
 */

public class ApiMethods {

    private Context context;
    private Api api;

    public ApiMethods(Context context) {
        this.context = context;
        this.api = new Api(context);
    }

    public void checkAuthorization(AuthCredentials authCredentials, ApiResponse apiResponse) {
        this.api.call(new ApiMethodBuilder("/user/login").addAuthCredentials(authCredentials), apiResponse);
    }

    public void listVms(ApiResponse apiResponse) {
        this.listVms(AuthCredentials.load(context), apiResponse);
    }

    public void listVms(AuthCredentials authCredentials, ApiResponse apiResponse) {
        this.api.call(new ApiMethodBuilder("/vm/list").addAuthCredentials(authCredentials), apiResponse);
    }

    public void assignVm(String pool, ApiResponse apiResponse) {
        this.assignVm(AuthCredentials.load(context), pool, apiResponse);
    }

    public void assignVm(AuthCredentials authCredentials, String pool, ApiResponse apiResponse) {
        this.api.call(new ApiMethodBuilder("/vm/assign/" + pool).addAuthCredentials(authCredentials), apiResponse);
    }

    public void cancelAll() {
        this.api.cancelAll();
    }
}
