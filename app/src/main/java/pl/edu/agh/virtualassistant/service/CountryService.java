package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.android.volley.VolleyError;

import java.util.function.Consumer;
import java.util.function.Function;

import pl.edu.agh.virtualassistant.infrastructure.CountryClient;
import pl.edu.agh.virtualassistant.model.country.Country;
import pl.edu.agh.virtualassistant.model.country.ShortCountry;

public class CountryService {

    public static void getCountryInfo(Context context, String country,
                                      Consumer<ShortCountry> onResponseCallback,
                                      Consumer<VolleyError> onErrorCallback) {
        CountryClient.getCountryInfo(context, country, getOnResponseCallback(onResponseCallback),
                onErrorCallback);
    }

    private static Consumer<Country> getOnResponseCallback(Consumer<ShortCountry> onResponseCallback) {
        return country -> onResponseCallback.accept(getConverter().apply(country));
    }

    private static Function<Country, ShortCountry> getConverter() {
        return country -> new ShortCountry(country.getName(), country.getRegion(), country.getCapital(),
                country.getPopulation(), country.getCurrencies().get(0).getName());
    }

}
