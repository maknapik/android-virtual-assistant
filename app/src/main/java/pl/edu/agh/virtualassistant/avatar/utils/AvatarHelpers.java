package pl.edu.agh.virtualassistant.avatar.utils;

import java.util.ArrayList;
import java.util.List;

public class AvatarHelpers {
    // dummy implementation for test only
    public static List<Integer> getExpressions(String message) {
        String[] splitMessage = message.split(" ");
        List<Integer> expressions = new ArrayList<>();
        for (String word : splitMessage) {
            if (word.contains("e")) {
                for (int i = 0; i < countLetterOccurrence(word, 'e'); i++) {
                    expressions.add(2);
                }
            }
            if (word.contains("a")) {
                for (int i = 0; i < countLetterOccurrence(word, 'a'); i++) {
                    expressions.add(3);
                }
            }
            if (word.contains("i")) {
                for (int i = 0; i < countLetterOccurrence(word, 'i'); i++) {
                    expressions.add(4);
                }
            }
            if (word.contains("u")) {
                for (int i = 0; i < countLetterOccurrence(word, 'u'); i++) {
                    expressions.add(5);
                }
            }
            if (word.contains("y")) {
                for (int i = 0; i < countLetterOccurrence(word, 'y'); i++) {
                    expressions.add(6);
                }
            }
            if (word.contains("a")) {
                for (int i = 0; i < countLetterOccurrence(word, 'a'); i++) {
                    expressions.add(7);
                }
            }
        }

        return expressions;
    }

    private static int countLetterOccurrence(String input, char letter) {
        return (int) input.chars().filter(ch -> ch == letter).count();
    }

}
