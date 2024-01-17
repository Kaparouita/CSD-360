package models;

public class Car extends Vehicle {
    // Add your Car-specific code here
    private int numberOfSeats;
    private String licensePlate;
    private String carType;

    public Car( String color,  String brand, String model,String carType ,String vehicleType, int numberOfSeats, String licensePlate, int kilometers, int dailyCost,Boolean isRented,String status) {
        super( color, brand, model, "car", kilometers, dailyCost,isRented,status);
        this.numberOfSeats = numberOfSeats;
        this.licensePlate = licensePlate;
        this.carType = carType;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public String getLicensePlate() {
        return licensePlate;
    }

    public void setLicensePlate(String licensePlate) {
        this.licensePlate = licensePlate;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getCarType() {
        return carType;
    }
}

