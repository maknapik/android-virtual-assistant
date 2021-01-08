package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.interaction.Interaction;

public class WeatherInteractionProvider implements Interaction {

    private Interaction nextInteraction;

    public WeatherInteractionProvider(Interaction nextInteraction) {
        this.nextInteraction = nextInteraction;
    }

    @Override
    public boolean accept(String userUtterance) {
        return userUtterance.contains("weather");
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        currentKnowledge.put("action", "weather");

        nextInteraction.process(userUtterance, currentKnowledge);
    }

}
