package pl.edu.agh.virtualassistant.converter;

import java.util.ArrayList;
import java.util.List;

import pl.edu.agh.virtualassistant.model.weather.CitiesInCircleWeather;
import pl.edu.agh.virtualassistant.model.weather.ListItem;
import pl.edu.agh.virtualassistant.model.weather.LocationDailyWeather;
import pl.edu.agh.virtualassistant.model.weather.ShortWeather;
import pl.edu.agh.virtualassistant.model.weather.Weather;
import pl.edu.agh.virtualassistant.model.weather.WeatherItem;

public class OpenWeatherConverter {

    private static final String NO_DESCRIPTION_LABEL = "description unavailable";
    private static final double KELVIN = 273.0;

    public static String getDescription(Weather weather) {
        List<WeatherItem> weatherItemList = weather.getWeather();

        return getDescriptionFromWeatherItemList(weatherItemList);
    }

    private static String getDescriptionFromWeatherItemList(List<WeatherItem> weatherItemList) {
        return weatherItemList.isEmpty() ? NO_DESCRIPTION_LABEL : weatherItemList.get(0).getDescription();
    }

    public static String getDescription(LocationDailyWeather locationDailyWeather) {
        List<WeatherItem> weatherItemList = locationDailyWeather.getCurrent().getWeather();

        return getDescriptionFromWeatherItemList(weatherItemList);
    }

    public static ShortWeather getShortWeather(Weather weather) {
        String city = weather.getName();
        String description = weather.getWeather().get(0).getDescription();
        double temperature = getTemperature(weather);
        int humidity = getHumidity(weather);
        double windSpeed = getWindSpeed(weather);

        return new ShortWeather(city, description, temperature, humidity, windSpeed);
    }

    public static double getTemperature(Weather weather) {
        return weather.getMain().getTemp() - KELVIN;
    }

    public static double getTemperature(LocationDailyWeather locationDailyWeather) {
        return locationDailyWeather.getCurrent().getTemp() - KELVIN;
    }

    public static int getHumidity(Weather weather) {
        return weather.getMain().getHumidity();
    }

    public static int getHumidity(LocationDailyWeather locationDailyWeather) {
        return locationDailyWeather.getCurrent().getHumidity();
    }

    public static double getWindSpeed(Weather weather) {
        return weather.getWind().getSpeed();
    }

    public static List<ShortWeather> getShortWeathers(CitiesInCircleWeather citiesInCircleWeather) {
        List<ShortWeather> shortWeathers = new ArrayList<>();
        for (ListItem listItem : citiesInCircleWeather.getList()) {
            shortWeathers.add(getShortWeathersFromListItem(listItem));
        }

        return shortWeathers;
    }

    private static ShortWeather getShortWeathersFromListItem(ListItem listItem) {
        String city = listItem.getName();
        String description = listItem.getWeather().get(0).getDescription();
        double temperature = listItem.getMain().getTemp();
        int humidity = listItem.getMain().getHumidity();
        double windSpeed = listItem.getWind().getSpeed();

        return new ShortWeather(city, description, temperature, humidity, windSpeed);
    }

}
