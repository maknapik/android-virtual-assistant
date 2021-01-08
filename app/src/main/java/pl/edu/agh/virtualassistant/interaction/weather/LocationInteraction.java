package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.interaction.Interaction;
import pl.edu.agh.virtualassistant.service.LocationService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class LocationInteraction implements Interaction {

    private Interaction nextInteraction;
    private MainActivity mainActivity;
    private VoiceControl voiceControl;
    private Consumer<String> onRecognitionResult;

    public LocationInteraction(Interaction nextInteraction, MainActivity mainActivity,
                               VoiceControl voiceControl, Consumer<String> onRecognitionResult) {
        this.nextInteraction = nextInteraction;
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
        this.onRecognitionResult = onRecognitionResult;
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        Optional<String> retrievedCityName = findCityName(userUtterance);

        if (retrievedCityName.isPresent()) {
            currentKnowledge.put("city", retrievedCityName.get());

            nextInteraction.process(userUtterance, currentKnowledge);
        } else if (userAskedForCurrentLocation(userUtterance)) {
            LocationService.getLocation(mainActivity, (latitude, longitude) -> {
                currentKnowledge.put("latitude", latitude);
                currentKnowledge.put("longitude", longitude);

                nextInteraction.process(userUtterance, currentKnowledge);
            });
        } else {
            voiceControl.say("Say the name of the city you want to get the " + currentKnowledge.get("action") + " for.");
            voiceControl.setSpeechRecognitionCallbacks(null, userAnswer -> {
                voiceControl.setSpeechRecognitionCallbacks(null, onRecognitionResult);
                currentKnowledge.put("city", userAnswer);

                nextInteraction.process(userUtterance, currentKnowledge);
            });
        }
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

    private boolean userAskedForCurrentLocation(String userUtterance) {
        return userUtterance.contains("current location") || userUtterance.contains("here");
    }

}
