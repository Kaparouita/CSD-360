package models;

public class Scooter extends Vehicle{

    private String uniqueNumber;

    public Scooter( String color, String brand, String model, String type, String uniqueNumber, int kilometers, int dailyCost) {
        super( color,  brand, model, "scooter", kilometers, dailyCost);
        this.uniqueNumber = uniqueNumber;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }
    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}
