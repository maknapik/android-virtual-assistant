package pl.edu.agh.virtualassistant.infrastructure;

import android.content.Context;
import android.util.ArrayMap;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.Map;
import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.model.joke.JokeResponse;

public class JokeClient {
    private final static String JOKE_API_ENDPOINT = "https://api.jokes.one/jod";

    public static void getRandomJoke(Context context, Consumer<JokeResponse> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        HttpClient.sendRequestWithHeaders(context, Request.Method.GET, JOKE_API_ENDPOINT, getRequestHeaders(), null,
                getOnResponseCallback(onResponseCallback, JokeResponse.class),
                onErrorCallback);
    }

    private static Map<String, String> getRequestHeaders() {
        Map<String, String> params = new ArrayMap<>();
        params.put("Content-Type", "application/json");
        return params;
    }

    private static <T> Consumer<JSONObject> getOnResponseCallback(Consumer<T> onResponseCallbackExternal,
                                                                  Class objectClass) {
        return jsonObject -> {
            onResponseCallbackExternal.accept(getResponse(jsonObject, objectClass));
        };
    }

    private static <T> T getResponse(JSONObject jsonObject, Class objectClass) {
        return (T) JSON.parseObject(String.valueOf(jsonObject), objectClass);
    }
}
