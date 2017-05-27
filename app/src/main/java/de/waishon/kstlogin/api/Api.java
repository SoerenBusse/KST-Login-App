package de.waishon.kstlogin.api;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Credentials;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by Sören on 27.05.2017.
 */
public class Api {
    // URL zum REST-Server
    private static final String BASE_URL = "https://api.mts.kolleg-st-thomas.de/v2";

    // HTTP Client
    private final OkHttpClient okHttpClient = new OkHttpClient();

    // Wird der Request abgebrochen?
    private boolean cancel = false;

    // Context der Application
    private Context context;

    /**
     * Konstruiert die API
     *
     * @param context
     */
    public Api(Context context) {
        this.context = context;
    }

    public void call(ApiMethodBuilder apiMethodBuilder, final ApiResponse apiResponse) {

        // Setze den HTTP Request zusammen
        Request.Builder requestBuilder = new Request.Builder()
                .url(BASE_URL + apiMethodBuilder.getRestPath());

        // Falls Zugangsdaten genutzt werden sollen, diese hinzufügen
        if (apiMethodBuilder.getAuthCredentials() != null) {
            requestBuilder.addHeader("Authorization", Credentials.basic(apiMethodBuilder.getAuthCredentials().getUsername(), apiMethodBuilder.getAuthCredentials().getPassword()));
        }

        // Neuen Request bauen
        Request request = requestBuilder.build();

        // UI informieren, dass neuer Request gestartet wurde
        apiResponse.onStart();

        // HTTP-Request asynchron ausführen
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                // Run Response on UI Thread
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        // Fehler loggen
                        Log.e("Api.java", e.getClass().getCanonicalName());

                        // Ist ein Fehler von diesem Typ?
                        if (e instanceof SocketTimeoutException || e instanceof UnknownHostException) {
                            apiResponse.onError(new ApiError(context, ApiError.Code.NETWORK_CONNECTION_PROBLEM));
                            return;
                        }

                        // Unbekannten Fehler zurückgeben
                        apiResponse.onError(new ApiError(context, ApiError.Code.UNKNOWN));
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                // Body auslesen
                final String body = response.body().string();

                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            JSONObject responseJson = new JSONObject(body);

                            // War die Antwort nicht erfolgreich?
                            if (!response.isSuccessful()) {
                                apiResponse.onError(new ApiError(context, responseJson.has("code") ? responseJson.getInt("code") : ApiError.Code.JSON_PARSING.ordinal()));
                                return;
                            }

                            apiResponse.onFinish(responseJson);
                        } catch (JSONException e) {
                            apiResponse.onError(new ApiError(context, ApiError.Code.JSON_PARSING));
                        }
                    }
                });
            }
        });
    }

    public void cancelAll() {
        cancel = true;
    }
}
