package pl.edu.agh.virtualassistant.converter;

import pl.edu.agh.virtualassistant.model.joke.JokeResponse;

public class JokeConverter {
    public static String getJoke(JokeResponse jokeResponse) {
        return jokeResponse.getContents().getFirstJoke().getText();
    }
}
