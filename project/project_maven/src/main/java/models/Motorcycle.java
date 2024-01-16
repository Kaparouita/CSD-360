package models;

public class Motorcycle extends Vehicle{
    private String licensePlate;

    public Motorcycle( String color,  String brand, String model, String licensePlate, int kilometers, int dailyCost,Boolean isRented,String status) {
        super( color,  brand, model, "motorcycle", kilometers, dailyCost,isRented,status);
        this.licensePlate = licensePlate;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

}
