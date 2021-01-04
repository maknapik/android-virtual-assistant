package pl.edu.agh.virtualassistant.chat;

import android.text.TextUtils;
import org.alicebot.ab.Bot;
import org.alicebot.ab.Chat;

public class ChatBot {
    private final Bot bot;
    private final Chat chat;

    public ChatBot(String ms) {
        bot = new Bot("alice", ms, "chat");
        chat = new Chat(bot);
    }

    public String askBot(String message) {
        String response = chat.multisentenceRespond(message);

        if (TextUtils.isEmpty(message)) {
            return "I don't understand.";
        }

        return response;
    }
}
