package pl.edu.agh.virtualassistant.model.joke;

import java.util.List;

public class Contents {
    List<Joke> jokes;

    public Joke getFirstJoke() {
        return jokes.size() == 0 ? new Joke() : jokes.get(0);
    }

    public List<Joke> getJokes() {
        return jokes;
    }

    public void setJokes(List<Joke> jokes) {
        this.jokes = jokes;
    }
}
