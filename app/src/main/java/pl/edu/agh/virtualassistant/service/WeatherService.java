package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import pl.edu.agh.virtualassistant.converter.OpenWeatherConverter;
import pl.edu.agh.virtualassistant.infrastructure.OpenWeatherClient;
import pl.edu.agh.virtualassistant.model.ShortWeather;

public class WeatherService {

    public static void getDescriptionForCity(Context context, String cityName, Consumer<String> onResponseCallback,
                                             Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getDescription),
                onErrorCallback);
    }

    private static <T, K> Consumer<T> getOnResponseCallback(Consumer<K> onResponseCallback,
                                                            Function<T, K> getValue) {
        return response -> onResponseCallback.accept(getValue.apply(response));
    }

    public static void getShortWeatherForCity(Context context, String cityName,
                                              Consumer<ShortWeather> onResponseCallback,
                                              Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getShortWeather),
                onErrorCallback);
    }

    public static void getTemperatureForCity(Context context, String cityName,
                                             Consumer<Double> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getTemperature),
                onErrorCallback);
    }

    public static void getHumidityForCity(Context context, String cityName, Consumer<Integer> onResponseCallback,
                                          Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getHumidity),
                onErrorCallback);
    }



    public static void getWindSpeedForCity(Context context, String cityName, Consumer<Double> onResponseCallback,
                                           Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getCurrentWeatherForCity(context, cityName,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getWindSpeed),
                onErrorCallback);
    }

    public static void getDescriptionForLocation(Context context, String latitude, String longitude,
                                                 Consumer<String> onResponseCallback,
                                                 Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getDailyWeatherForLocation(context, latitude, longitude,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getDescription),
                onErrorCallback);
    }

    public static void getTemperatureForLocation(Context context, String latitude, String longitude,
                                             Consumer<Double> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getDailyWeatherForLocation(context, latitude, longitude,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getTemperature),
                onErrorCallback);
    }

    public static void getHumidityForLocation(Context context, String latitude, String longitude,
                                                 Consumer<Integer> onResponseCallback, Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getDailyWeatherForLocation(context, latitude, longitude,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getHumidity),
                onErrorCallback);
    }

    public static void getShortWeatherForCitiesInCircle(Context context, String latitude,
                                                        String longitude, int citiesNumber,
                                                        Consumer<List<ShortWeather>> onResponseCallback,
                                                        Consumer<VolleyError> onErrorCallback) {
        OpenWeatherClient.getWeatherInCitiesInCircle(context, latitude, longitude, citiesNumber,
                getOnResponseCallback(onResponseCallback, OpenWeatherConverter::getShortWeatherList),
                onErrorCallback);
    }

}
