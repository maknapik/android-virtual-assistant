package pl.edu.agh.virtualassistant.interaction;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.chat.ChatBot;
import pl.edu.agh.virtualassistant.interaction.weather.HumidityInteraction;
import pl.edu.agh.virtualassistant.interaction.weather.HumidityInteractionProvider;
import pl.edu.agh.virtualassistant.interaction.weather.LocationInteraction;
import pl.edu.agh.virtualassistant.interaction.weather.TemperatureInteraction;
import pl.edu.agh.virtualassistant.interaction.weather.TemperatureInteractionProvider;
import pl.edu.agh.virtualassistant.interaction.weather.WeatherInteraction;
import pl.edu.agh.virtualassistant.interaction.weather.WeatherInteractionProvider;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class InteractionFactory {

    private final List<Interaction> providers;

    InteractionFactory(MainActivity mainActivity, VoiceControl voiceControl,
                       Consumer<String> onRecognitionResult, ChatBot chatBot) {
        providers = new ArrayList<>();

        providers.add(getWeatherInteractionFlow(mainActivity, voiceControl, onRecognitionResult));
        providers.add(getTemperatureInteractionFlow(mainActivity, voiceControl, onRecognitionResult));
        providers.add(geHumidityInteractionFlow(mainActivity, voiceControl, onRecognitionResult));
        providers.add(new CountryInformationInteractionProvider(mainActivity, voiceControl));
        providers.add(new JokeInteractionProvider(mainActivity, voiceControl));
        providers.add(new ChatBotInteractionProvider(voiceControl, chatBot));
    }

    private WeatherInteractionProvider getWeatherInteractionFlow(MainActivity mainActivity,
                                                                 VoiceControl voiceControl,
                                                                 Consumer<String> onRecognitionResult) {
        WeatherInteraction weatherInteraction = new WeatherInteraction(mainActivity, voiceControl);
        LocationInteraction locationInteraction = new LocationInteraction(weatherInteraction,
                mainActivity, voiceControl, onRecognitionResult);
        return new WeatherInteractionProvider(locationInteraction);
    }

    private TemperatureInteractionProvider getTemperatureInteractionFlow(MainActivity mainActivity,
                                                                         VoiceControl voiceControl,
                                                                         Consumer<String> onRecognitionResult) {
        TemperatureInteraction temperatureInteraction = new TemperatureInteraction(mainActivity, voiceControl);
        LocationInteraction locationInteraction = new LocationInteraction(temperatureInteraction,
                mainActivity, voiceControl, onRecognitionResult);
        return new TemperatureInteractionProvider(locationInteraction);
    }

    private HumidityInteractionProvider geHumidityInteractionFlow(MainActivity mainActivity,
                                                                  VoiceControl voiceControl,
                                                                  Consumer<String> onRecognitionResult) {
        HumidityInteraction humidityInteraction = new HumidityInteraction(mainActivity, voiceControl);
        LocationInteraction locationInteraction = new LocationInteraction(humidityInteraction,
                mainActivity, voiceControl, onRecognitionResult);
        return new HumidityInteractionProvider(locationInteraction);
    }

    public void process(String userUtterance) {
        for (Interaction provider : providers) {
            if (provider.accept(userUtterance)) {
                provider.process(userUtterance, new HashMap<>());
                return;
            }
        }
    }

}
