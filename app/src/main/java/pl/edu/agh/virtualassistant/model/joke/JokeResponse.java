package pl.edu.agh.virtualassistant.model.joke;

public class JokeResponse {
    private Success success;
    private Contents contents;
    private String copyright;

    public Contents getContents() {
        return contents != null ? contents : new Contents();
    }

    public Success getSuccess() {
        return success;
    }

    public void setSuccess(Success success) {
        this.success = success;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }
}