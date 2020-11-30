package pl.edu.agh.virtualassistant.model.joke;

public class JokeResponse {
    private Success success;
    private Contents contents;
    private String copyright;

    public Contents getContents() {
        return contents;
    }
}