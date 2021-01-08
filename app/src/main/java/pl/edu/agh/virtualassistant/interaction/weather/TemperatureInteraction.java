package pl.edu.agh.virtualassistant.interaction.weather;

import java.text.DecimalFormat;
import java.util.HashMap;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.interaction.Interaction;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class TemperatureInteraction implements Interaction {

    private static final DecimalFormat temperatureFormat = new DecimalFormat("0.#");

    private MainActivity mainActivity;
    private VoiceControl voiceControl;

    public TemperatureInteraction(MainActivity mainActivity, VoiceControl voiceControl) {
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        if (currentKnowledge.containsKey("city")) {
            String city = currentKnowledge.get("city");
            WeatherService.getTemperatureForCity(mainActivity, city,
                    temperature -> say("Temperature in " + city + " is " + temperatureFormat.format(temperature) + " degrees Celsius."),
                    error -> say("I cannot check the temperature for this place."));
        } else if (currentKnowledge.containsKey("latitude")
                && currentKnowledge.containsKey("longitude")) {
            String latitude = currentKnowledge.get("latitude");
            String longitude = currentKnowledge.get("longitude");
            WeatherService.getTemperatureForLocation(mainActivity, latitude, longitude,
                    temperature -> say("The temperature at current location is " + temperatureFormat.format(temperature) + " degrees Celsius."),
                    error -> say("I cannot check the temperature for current location"));
        }
    }

    private void say(String response) {
        voiceControl.say(response);
    }

}
