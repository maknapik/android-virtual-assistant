package pl.edu.agh.virtualassistant.infrastructure;

import android.annotation.SuppressLint;
import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class VolleyClient {

    @SuppressLint("StaticFieldLeak")
    private static VolleyClient instance;

    private final Context context;

    private RequestQueue requestQueue;

    private VolleyClient(Context context) {
        this.context = context;
        requestQueue = getRequestQueue();
    }

    public static synchronized VolleyClient getInstance(Context context) {
        if (instance == null) {
            instance = new VolleyClient(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> request) {
        getRequestQueue().add(request);
    }

}
