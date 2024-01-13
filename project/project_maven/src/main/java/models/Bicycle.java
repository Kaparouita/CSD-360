package models;

public class Bicycle extends Vehicle{

    private String uniqueNumber;

    public Bicycle( String color, String brand, String model, String type, String uniqueNumber, int kilometers, int dailyCost) {
        super( color,  brand, model, "bicycle", kilometers,dailyCost);
        this.uniqueNumber = uniqueNumber;
    }

    public String getUniqueNumber() {
        return uniqueNumber;
    }
    public void setUniqueNumber(String uniqueNumber) {
        this.uniqueNumber = uniqueNumber;
    }
}
