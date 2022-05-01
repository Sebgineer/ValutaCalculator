package dk.zeb.valutacalculator.currency;

public class Rate {

    public String name;
    public double spotRate;

    public Rate(String name, double spotRate) {
        this.name = name;
        this.spotRate = spotRate;
    }

    public String getName() {
        return this.name;
    }

    public double getSpotRate() {
        return this.spotRate;
    }
}
