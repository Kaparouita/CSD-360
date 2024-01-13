package models;

public class Motorcycle extends Vehicle{
    private String licensePlate;

    public Motorcycle( String color,  String brand, String model, String licensePlate, int kilometers, int dailyCost) {
        super( color,  brand, model, "motorcycle", kilometers, dailyCost);
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
