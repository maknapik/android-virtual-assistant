package pl.edu.agh.virtualassistant.infrastructure;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.VolleyError;

import org.json.JSONObject;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.error.Notification;
import pl.edu.agh.virtualassistant.model.CitiesInCircleWeather;
import pl.edu.agh.virtualassistant.model.LocationDailyWeather;
import pl.edu.agh.virtualassistant.model.Weather;

public class OpenWeatherClient {

    private static final String API_KEY = "bf540f685b579cf130b18b50941b4459";
    private static final String API_ENDPOINT = "http://api.openweathermap.org/data/2.5/";

    public static void getCurrentWeatherForCity(Context context, String cityName,
                                                Consumer<Weather> onResponseCallback) {
        getCurrentWeatherForCity(context, cityName, onResponseCallback, getOnErrorResponseCallback(context));
    }

    public static void getCurrentWeatherForCity(Context context, String cityName,
                                                Consumer<Weather> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        String url = API_ENDPOINT + "weather?q=" + cityName + getApiKeySuffix();

        HttpClient.sendRequest(context, Request.Method.GET, url, null,
                getOnResponseCallback(onResponseCallback, Weather.class),
                onErrorCallback);
    }

    private static String getApiKeySuffix() {
        return "&appid=" + API_KEY;
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

    private static Consumer<VolleyError> getOnErrorResponseCallback(Context context) {
        return jsonObject -> {
            Notification.showCommunicationError(context);
        };
    }

    public static void getDailyWeatherForLocation(Context context, String latitude, String longitude,
                                                  Consumer<LocationDailyWeather> onResponseCallback) {
        String url = API_ENDPOINT + "onecall?lat=" + latitude + "&lon=" + longitude + getApiKeySuffix();

        HttpClient.sendRequest(context, Request.Method.GET, url, null,
                getOnResponseCallback(onResponseCallback, LocationDailyWeather.class),
                getOnErrorResponseCallback(context));
    }

    public static void getDailyWeatherForLocation(Context context, String latitude, String longitude,
                                                  Consumer<LocationDailyWeather> onResponseCallback,
                                                  Consumer<VolleyError> onErrorCallback) {
        String url = API_ENDPOINT + "onecall?lat=" + latitude + "&lon=" + longitude + getApiKeySuffix();

        HttpClient.sendRequest(context, Request.Method.GET, url, null,
                getOnResponseCallback(onResponseCallback, LocationDailyWeather.class),
                onErrorCallback);
    }

    public static void getWeatherInCitiesInCircle(Context context, String latitude, String longitude,
                                                  int citiesNumber,
                                                  Consumer<CitiesInCircleWeather> onResponseCallback) {
        String url = API_ENDPOINT + "find?lat=" + latitude + "&lon=" + longitude + "&cnt="
                + citiesNumber + getApiKeySuffix();

        HttpClient.sendRequest(context, Request.Method.GET, url, null,
                getOnResponseCallback(onResponseCallback, CitiesInCircleWeather.class),
                getOnErrorResponseCallback(context));
    }

    public static void getWeatherInCitiesInCircle(Context context, String latitude, String longitude,
                                                  int citiesNumber,
                                                  Consumer<CitiesInCircleWeather> onResponseCallback,
                                                  Consumer<VolleyError> onErrorCallback) {
        String url = API_ENDPOINT + "find?lat=" + latitude + "&lon=" + longitude + "&cnt="
                + citiesNumber + getApiKeySuffix();

        HttpClient.sendRequest(context, Request.Method.GET, url, null,
                getOnResponseCallback(onResponseCallback, CitiesInCircleWeather.class),
                onErrorCallback);
    }

}
