package pl.edu.agh.virtualassistant.model.country;

public class ShortCountry {

    private String name;
    private String region;
    private String capital;
    private long population;
    private String currency;

    public ShortCountry(String name, String region, String capital, long population, String currency) {
        this.name = name;
        this.region = region;
        this.capital = capital;
        this.population = population;
        this.currency = currency;
    }

    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCapital() {
        return capital;
    }

    public long getPopulation() {
        return population;
    }

    public String getCurrency() {
        return currency;
    }

}
