package pl.edu.agh.virtualassistant.interaction;

import android.content.res.Resources;

import java.text.DecimalFormat;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.service.JokeService;
import pl.edu.agh.virtualassistant.service.LocationService;
import pl.edu.agh.virtualassistant.service.WeatherService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class ResponseResolver {

    private static final DecimalFormat temperatureFormat = new DecimalFormat("0.#");
    private final VoiceControl voiceControl;
    private final MainActivity mainActivity;

    public ResponseResolver(VoiceControl voiceControl, MainActivity mainActivity) {
        this.voiceControl = voiceControl;
        this.mainActivity = mainActivity;
    }

    public void respond(String userUtterance) {
        Optional<String> retrievedCityName = findCityName(userUtterance);
        if (userUtterance.contains("temperature")) {
            if (retrievedCityName.isPresent()) {
                WeatherService.getTemperatureForCity(mainActivity, retrievedCityName.get(),
                        temperature -> say("Temperature in " + retrievedCityName.get() + " is " + temperatureFormat.format(temperature) + " degrees Celsius."),
                        error -> say("I cannot check the temperature for this place."));
                return;
            }
            if (userAskedForCurrentLocation(userUtterance)) {
                LocationService.getLocation(mainActivity, (latitude, longitude) ->
                        WeatherService.getTemperatureForLocation(mainActivity, latitude, longitude,
                                temperature -> say("The temperature at current location is " + temperatureFormat.format(temperature) + " degrees Celsius."),
                                error -> say("I cannot check the temperature for current location")));
                return;
            }
            say("Say the name of the city you want to get the temperature for.");
            voiceControl.setSpeechRecognitionCallbacks(null,
                    userAnswer -> WeatherService.getTemperatureForCity(mainActivity, userAnswer,
                            temperature -> {
                                say("Temperature in " + userAnswer + " is " + temperatureFormat.format(temperature) + " degrees Celsius.");
                                voiceControl.setSpeechRecognitionCallbacks(null, this::respond);
                            },
                            error -> say("I cannot check the temperature for this place.")));
        } else if (userUtterance.contains("humidity") && retrievedCityName.isPresent()) {
            WeatherService.getHumidityForCity(mainActivity, retrievedCityName.get(),
                    humidity -> say("Humidity in " + retrievedCityName.get() + " is " + humidity + "%"),
                    error -> say("I cannot check the humidity for this place."));
        } else if (userAskedForCurrentLocation(userUtterance) && userUtterance.contains("weather")) {
            LocationService.getLocation(mainActivity, (latitude, longitude) -> {
                WeatherService.getDescriptionForLocation(mainActivity, latitude, longitude,
                        description -> say("The weather at current location can be described as " + description),
                        error -> say("I cannot check the weather for current location"));
            });
        } else if (userUtterance.contains("joke")) {
            JokeService.getRandomJoke(mainActivity,
                    joke -> say("Joke for today is: " + joke),
                    error -> say("I don't know any jokes."));
        } else {
            say("I don't understand.");
        }
    }

    private static boolean userAskedForCurrentLocation(String userUtterance) {
        return userUtterance.contains("current location") || userUtterance.contains("here");
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
        return Optional.ofNullable(matcher.group(1));
    }
}
