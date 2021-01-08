package pl.edu.agh.virtualassistant.interaction.weather;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.interaction.Interaction;

public class HumidityInteractionProvider implements Interaction {

    private Interaction nextInteraction;

    public HumidityInteractionProvider(Interaction nextInteraction) {
        this.nextInteraction = nextInteraction;
    }

    @Override
    public boolean accept(String userUtterance) {
        return userUtterance.contains("humidity");
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        currentKnowledge.put("action", "humidity");

        nextInteraction.process(userUtterance, currentKnowledge);
    }

}
