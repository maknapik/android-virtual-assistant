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

import pl.edu.agh.virtualassistant.infrastructure.CountryClient;
import pl.edu.agh.virtualassistant.infrastructure.OpenWeatherClient;
import pl.edu.agh.virtualassistant.model.country.ShortCountry;
import pl.edu.agh.virtualassistant.model.weather.ShortWeather;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;

@RunWith(PowerMockRunner.class)
@PrepareForTest(CountryClient.class)
public class CountryServiceTest {
    private static final String COUNTRY = "Poland";

    @Mock
    private Context contextMock;
    @Mock
    private Consumer<VolleyError> onErrorCallbackMock;
    @Mock
    private Consumer<ShortCountry> onResponseCallbackMock;

    @Test
    public void shouldAskCountryClientAboutCountryInfo() throws Exception {
        // given
        PowerMockito.mockStatic(CountryClient.class);
        PowerMockito.doNothing().when(CountryClient.class, "getCountryInfo",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        CountryService.getCountryInfo(contextMock, COUNTRY, onResponseCallbackMock, onErrorCallbackMock);

        // then
        PowerMockito.verifyStatic(Mockito.times(1));
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowExceptionWhenCallCountryClient() throws Exception {
        // given
        PowerMockito.mockStatic(CountryClient.class);
        PowerMockito.doThrow(new RuntimeException()).when(CountryClient.class, "getCountryInfo",
                any(Context.class), anyString(), any(Consumer.class), any(Consumer.class));

        // when
        CountryService.getCountryInfo(contextMock, COUNTRY, onResponseCallbackMock, onErrorCallbackMock);
    }

}