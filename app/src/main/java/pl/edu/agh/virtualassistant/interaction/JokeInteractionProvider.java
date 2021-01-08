package pl.edu.agh.virtualassistant.interaction;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.service.JokeService;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class JokeInteractionProvider implements Interaction {

    private MainActivity mainActivity;
    private VoiceControl voiceControl;

    public JokeInteractionProvider(MainActivity mainActivity, VoiceControl voiceControl) {
        this.mainActivity = mainActivity;
        this.voiceControl = voiceControl;
    }

    @Override
    public boolean accept(String userUtterance) {
        return userUtterance.contains("joke");
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        JokeService.getRandomJoke(mainActivity,
                joke -> say("Joke for today is: " + joke),
                error -> say("I don't know any jokes."));
    }

    private void say(String response) {
        voiceControl.say(response);
    }

}
