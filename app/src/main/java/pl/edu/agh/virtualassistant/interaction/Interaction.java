package pl.edu.agh.virtualassistant.interaction;

import java.util.HashMap;

public interface Interaction {

    default boolean accept(String userUtterance) {
        return true;
    }

    void process(String userUtterance, HashMap<String, String> currentKnowledge);

}
