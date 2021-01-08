package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.interaction.Interaction;

public class TemperatureInteractionProvider implements Interaction {

    private Interaction nextInteraction;

    public TemperatureInteractionProvider(Interaction nextInteraction) {
        this.nextInteraction = nextInteraction;
    }

    @Override
    public boolean accept(String userUtterance) {
        return userUtterance.contains("temperature");
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        currentKnowledge.put("action", "temperature");

        nextInteraction.process(userUtterance, currentKnowledge);
    }

}
