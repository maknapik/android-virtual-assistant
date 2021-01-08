package pl.edu.agh.virtualassistant.interaction;

import java.util.HashMap;

import pl.edu.agh.virtualassistant.chat.ChatBot;
import pl.edu.agh.virtualassistant.voice.VoiceControl;

public class ChatBotInteractionProvider implements Interaction {

    private VoiceControl voiceControl;
    private ChatBot chatBot;

    public ChatBotInteractionProvider(VoiceControl voiceControl, ChatBot chatBot) {
        this.voiceControl = voiceControl;
        this.chatBot = chatBot;
    }

    @Override
    public boolean accept(String userUtterance) {
        return true;
    }

    @Override
    public void process(String userUtterance, HashMap<String, String> currentKnowledge) {
        voiceControl.say(chatBot.askBot(userUtterance));
    }

}
