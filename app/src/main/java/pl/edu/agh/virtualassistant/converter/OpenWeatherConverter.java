package pl.edu.agh.virtualassistant.converter;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.virtualassistant.model.CitiesInCircleWeather;
import pl.edu.agh.virtualassistant.model.ListItem;
import pl.edu.agh.virtualassistant.model.ShortWeather;
import pl.edu.agh.virtualassistant.model.Weather;
import pl.edu.agh.virtualassistant.model.WeatherItem;

public class OpenWeatherConverter {

    private static final String NO_DESCRIPTION_LABEL = "description unavailable";
    private static final double KELVIN = 273.0;

    public static String getDescription(Weather weather) {
        List<WeatherItem> weatherItemList = weather.getWeather();
        return weatherItemList.isEmpty() ? NO_DESCRIPTION_LABEL : weatherItemList.get(0).getDescription();
    }

    public static ShortWeather getShortWeather(Weather weather) {
        double temperature = getTemperature(weather);
        int humidity = getHumidity(weather);
        double windSpeed = getWindSpeed(weather);

        return new ShortWeather(temperature, humidity, windSpeed);
    }

    public static double getTemperature(Weather weather) {
        return weather.getMain().getTemp() - KELVIN;
    }

    public static int getHumidity(Weather weather) {
        return weather.getMain().getHumidity();
    }

    public static double getWindSpeed(Weather weather) {
        return weather.getWind().getSpeed();
    }

    public static List<ShortWeather> getShortWeatherList(CitiesInCircleWeather citiesInCircleWeather) {
        List<ShortWeather> shortWeatherList = new ArrayList<>();
        for (ListItem listItem : citiesInCircleWeather.getList()) {
            shortWeatherList.add(getShortWeatherFromListItem(listItem));
        }

        return shortWeatherList;
    }

    private static ShortWeather getShortWeatherFromListItem(ListItem listItem) {
        double temperature = listItem.getMain().getTemp();
        int humidity = listItem.getMain().getHumidity();
        double windSpeed = listItem.getWind().getSpeed();

        return new ShortWeather(temperature, humidity, windSpeed);
    }

}
