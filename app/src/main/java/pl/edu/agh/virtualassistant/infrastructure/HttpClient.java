package pl.edu.agh.virtualassistant.infrastructure;

import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.function.Consumer;

public class HttpClient {

    public static void sendRequest(Context context, int method, String url, JSONObject jsonRequest,
                                       Consumer<JSONObject> onResponseCallback,
                                       Consumer<VolleyError> onErrorResponseCallback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                jsonRequest, onResponseCallback::accept, onErrorResponseCallback::accept);

        VolleyClient.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
