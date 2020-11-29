package pl.edu.agh.virtualassistant.avatar.utils;

import java.util.ArrayList;
import java.util.List;

public class AvatarHelpers {

    public static List<Integer> getExpressions(String message) {
        String[] splitMessage = message.split(" ");
        List<Integer> expressions = new ArrayList<>();
        for (String word : splitMessage) {
            if (word.contains("e")) expressions.add(2);
            else if(word.contains("i")) expressions.add(4);
            else if(word.contains("o")) expressions.add(5);
            else if(word.contains("a")) expressions.add(0);
            else if(word.contains("y") || word.contains("u")) expressions.add(3);
            else expressions.add(1);
        }
        return expressions;
    }

}
