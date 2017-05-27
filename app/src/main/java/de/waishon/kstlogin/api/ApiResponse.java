package de.waishon.kstlogin.api;

import org.json.JSONObject;

/**
 * Created by Sören on 18.02.2017.
 */

public interface ApiResponse {
    void onError(ApiError apiError);
    void onFinish(JSONObject response);
    void onStart();
}
