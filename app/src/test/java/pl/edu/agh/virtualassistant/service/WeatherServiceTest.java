package pl.edu.agh.virtualassistant.service;

import android.content.Context;

import com.android.volley.VolleyError;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.infrastructure.OpenWeatherClient;
import pl.edu.agh.virtualassistant.model.weather.ShortWeather;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(OpenWeatherClient.class)
public class WeatherServiceTest {
    private static final String CITY_NAME = "Kraków";
    private static final String LONGITUDE = "20.00";
    private static final String LATITUDE = "50.00";

    @Mock
    private Context contextMock;
    @Mock
    private Consumer<VolleyError> onErrorCallbackMock;
    // TODO: fixme to generic type
    @Mock
    private Consumer<String> onStringResponseCallbackMock;
    @Mock
    private Consumer<Integer> onIntegerResponseCallbackMock;
    @Mock
    private Consumer<Double> onDoubleResponseCallbackMock;
    @Mock
    private Consumer<ShortWeather> onShortWeatherResponseCallbackMock;

    @Test
    public void shouldAskOpenWeatherClientAboutShortWeatherForCity() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getCurrentWeatherForCity",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getShortWeatherForCity(contextMock, CITY_NAME, onShortWeatherResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutDescriptionForCity() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getCurrentWeatherForCity",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getDescriptionForCity(contextMock, CITY_NAME, onStringResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutDescriptionForLocation() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getDailyWeatherForLocation",
                any(Context.class), anyString(), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getDescriptionForLocation(contextMock, LATITUDE, LONGITUDE, onStringResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutHumidityForCity() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getCurrentWeatherForCity",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getHumidityForCity(contextMock, CITY_NAME, onIntegerResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutHumidityForLocation() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getDailyWeatherForLocation",
                any(Context.class), anyString(), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getHumidityForLocation(contextMock, LATITUDE, LONGITUDE, onIntegerResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }



    @Test
    public void shouldAskOpenWeatherClientAboutTemperatureForCity() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getCurrentWeatherForCity",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getTemperatureForCity(contextMock, CITY_NAME, onDoubleResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutTemperatureForLocation() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getDailyWeatherForLocation",
                any(Context.class), anyString(), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getTemperatureForLocation(contextMock, LATITUDE, LONGITUDE, onDoubleResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test
    public void shouldAskOpenWeatherClientAboutWindSpeedForCity() throws Exception {
        // given
        PowerMockito.mockStatic(OpenWeatherClient.class);
        PowerMockito.doNothing().when(OpenWeatherClient.class, "getCurrentWeatherForCity",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        WeatherService.getWindSpeedForCity(contextMock, CITY_NAME, onDoubleResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

}