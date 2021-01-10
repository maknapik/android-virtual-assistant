package pl.edu.agh.virtualassistant.infrastructure;

import android.content.Context;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Map;
import java.util.function.Consumer;

public class HttpClient {

    public static void sendRequest(Context context, int method, String url, JSONObject jsonRequest,
                                       Consumer<JSONObject> onResponseCallback,
                                       Consumer<VolleyError> onErrorResponseCallback) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                jsonRequest, onResponseCallback::accept, onErrorResponseCallback::accept);

        VolleyClient.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

    public static void sendArrayRequest(Context context, int method, String url, JSONArray jsonRequest,
                                        Consumer<JSONArray> onResponseCallback,
                                        Consumer<VolleyError> onErrorResponseCallback) {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(method, url,
                jsonRequest, onResponseCallback::accept, onErrorResponseCallback::accept);

        VolleyClient.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public static void sendRequestWithHeaders(Context context, int method, String url, Map<String, String> mHeaders, JSONObject jsonRequest,
                                              Consumer<JSONObject> onResponseCallback,
                                              Consumer<VolleyError> onErrorResponseCallback) {

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url,
                jsonRequest, onResponseCallback::accept, onErrorResponseCallback::accept) {
            @Override
            public Map<String, String> getHeaders() {
                return mHeaders;
            }
        };

        VolleyClient.getInstance(context).addToRequestQueue(jsonObjectRequest);
    }

}
