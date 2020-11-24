package pl.edu.agh.virtualassistant;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.agh.virtualassistant.service.LocationService;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class RequestResolver {

    private static final DecimalFormat temperatureFormat = new DecimalFormat("0.#");
    private final VoiceControl voiceControl;
    private final MainActivity mainActivity;

    public RequestResolver(VoiceControl voiceControl, MainActivity mainActivity) {
        this.voiceControl = voiceControl;
        this.mainActivity = mainActivity;
    }

    public void respondToRequest(String userRequest) {
        Optional<String> retrievedCityName = findCityName(userRequest);
        if (userRequest.contains("temperature") && retrievedCityName.isPresent()) {
            WeatherService.getTemperatureForCity(mainActivity, retrievedCityName.get(),
                    temperature -> say("Temperature in " + retrievedCityName.get() + " is " + temperatureFormat.format(temperature) + " degrees Celsius."),
                    error -> say("I cannot check the temperature for this place."));
        } else if (userRequest.contains("humidity") && retrievedCityName.isPresent()) {
            WeatherService.getHumidityForCity(mainActivity, retrievedCityName.get(),
                    humidity -> say("Humidity in " + retrievedCityName.get() + " is " + humidity + "%"),
                    error -> say("I cannot check the humidity for this place."));
        } else if(userRequest.contains("current location") && userRequest.contains("weather")) {
            LocationService.getLocation(mainActivity, location -> {
                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());
                WeatherService.getDescriptionForLocation(mainActivity, latitude, longitude,
                        description -> say("The weather at current location can be described as " + description),
                        error -> say("I cannot check the weather for current location"));
            });

        } else {
            voiceControl.say("I don't understand.");
        }
    }

    private void say(String response) {
        voiceControl.say(response);
    }

    /**
     * Finds a name of a city in an utterance, assuming that:
     * - the first word in an utterance is NOT a part of a city name
     * - the first sequence of capitalized words in an utterance IS a city name
     */
    private Optional<String> findCityName(String userRequest) {
        String sentence = userRequest.substring(0, 1).toLowerCase() + userRequest.substring(1);
        Pattern cityPattern = Pattern.compile("(\\b(?:[A-Z][a-z]*\\b\\s*)+)");
        Matcher matcher = cityPattern.matcher(sentence);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.of(matcher.group(1));
    }
}
