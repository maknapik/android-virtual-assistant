package pl.edu.agh.virtualassistant.infrastructure;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.model.country.Country;

public class CountryClient {

    private final static String COUNTRY_API_ENDPOINT = "https://restcountries.eu/rest/v2/name/";

    public static void getCountryInfo(Context context, String country,
                                      Consumer<Country> onResponseCallback,
                                      Consumer<VolleyError> onErrorCallback) {
        HttpClient.sendArrayRequest(context, Request.Method.GET, COUNTRY_API_ENDPOINT + country,
                null, getOnResponseCallback(onResponseCallback, Country.class),
                onErrorCallback);
    }

    private static <T> Consumer<JSONArray> getOnResponseCallback(Consumer<T> onResponseCallbackExternal,
                                                                 Class objectClass) {
        return jsonArray -> {
            try {
                onResponseCallbackExternal.accept(getResponse((JSONObject) jsonArray.get(0), objectClass));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };
    }

    private static <T> T getResponse(JSONObject jsonObject, Class objectClass) {
        return (T) JSON.parseObject(String.valueOf(jsonObject), objectClass);
    }


}
