package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.interaction.Interaction;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class WeatherInteraction implements Interaction {

    private MainActivity mainActivity;
    private VoiceControl voiceControl;

    public WeatherInteraction(MainActivity mainActivity, VoiceControl voiceControl) {
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        if (currentKnowledge.containsKey("city")) {
            String city = currentKnowledge.get("city");
            WeatherService.getDescriptionForCity(mainActivity, city,
                    description -> say("The weather at " + city + " location can be described as " + description),
                    error -> say("I cannot check the weather for this place."));
        } else if (currentKnowledge.containsKey("latitude")
                && currentKnowledge.containsKey("longitude")) {
            String latitude = currentKnowledge.get("latitude");
            String longitude = currentKnowledge.get("longitude");
            WeatherService.getDescriptionForLocation(mainActivity, latitude, longitude,
                    description -> say("The weather at current location can be described as " + description),
                    error -> say("I cannot check the weather for current location"));
        }
    }

    private void say(String response) {
        voiceControl.say(response);
    }

}
