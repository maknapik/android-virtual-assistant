package pl.edu.agh.virtualassistant.interaction;

import pl.edu.agh.virtualassistant.MainActivity;
import pl.edu.agh.virtualassistant.chat.ChatBot;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class ResponseResolver {

    private final InteractionFactory interactionFactory;

    public ResponseResolver(VoiceControl voiceControl, MainActivity mainActivity, ChatBot chatBot) {
        interactionFactory = new InteractionFactory(mainActivity, voiceControl, this::respond, chatBot);
    }

    public void respond(String userUtterance) {
        interactionFactory.process(userUtterance);
    }

}
