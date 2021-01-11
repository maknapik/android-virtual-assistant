package pl.edu.agh.virtualassistant.interaction;

import java.util.HashMap;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.model.country.ShortCountry;
import pl.edu.agh.virtualassistant.service.CountryService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class CountryInformationInteractionProvider implements Interaction {

    private MainActivity mainActivity;
    private VoiceControl voiceControl;

    public CountryInformationInteractionProvider(MainActivity mainActivity, VoiceControl voiceControl) {
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
    }

    @Override
    public boolean accept(String userUtterance) {
        return userUtterance.contains("country");
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        Optional<String> retrievedCountryName = findCountry(userUtterance);

        retrievedCountryName.ifPresent(country -> CountryService.getCountryInfo(mainActivity, country,
                shortCountry -> {
                    say(getCountryDescription(shortCountry));
                },
                error -> say("I cannot check the information for given country")));
    }

    /**
     * Finds a name of a country in an utterance, assuming that:
     * - the first word in an utterance is NOT a part of a country name
     * - the first sequence of capitalized words in an utterance IS a country name
     */
    private Optional<String> findCountry(String userRequest) {
        String sentence = userRequest.substring(0, 1).toLowerCase() + userRequest.substring(1);
        Pattern countryPattern = Pattern.compile("(\\b(?:[A-Z][a-z]*\\b\\s*)+)");
        Matcher matcher = countryPattern.matcher(sentence);
        if (!matcher.find()) {
            return Optional.empty();
        }
        return Optional.ofNullable(matcher.group(1));
    }

    private void say(String response) {
        voiceControl.say(response);
    }

    private String getCountryDescription(ShortCountry shortCountry) {
        return shortCountry.getName() + " is located in " + shortCountry.getRegion() +
                ". The capital is " + shortCountry.getCapital() + ". The population counts " +
                shortCountry.getPopulation() + " people and the currency is " + shortCountry.getCurrency();
    }

}
