package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.interaction.Interaction;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class HumidityInteraction implements Interaction {

    private MainActivity mainActivity;
    private VoiceControl voiceControl;

    public HumidityInteraction(MainActivity mainActivity, VoiceControl voiceControl) {
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        if (currentKnowledge.containsKey("city")) {
            String city = currentKnowledge.get("city");
            WeatherService.getHumidityForCity(mainActivity, city,
                    humidity -> say("Humidity in " + city + " is " + humidity + "%"),
                    error -> say("I cannot check the humidity for this place."));
        } else if (currentKnowledge.containsKey("latitude")
                && currentKnowledge.containsKey("longitude")) {
            String latitude = currentKnowledge.get("latitude");
            String longitude = currentKnowledge.get("longitude");
            WeatherService.getHumidityForLocation(mainActivity, latitude, longitude,
                    humidity -> say("Humidity at current location is " + humidity + "%"),
                    error -> say("I cannot check the humidity for current location."));
        }
    }

    private void say(String response) {
        voiceControl.say(response);
    }

}
