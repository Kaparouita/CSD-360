package models;

public abstract class Vehicle {
    
    private String color;
    private String brand;
    private String model;
    private String type; // car, truck, motorcycle, etc.  
    private int kilometers;
    private int dailyCost;  
    
    public Vehicle(String color,  String brand, String model, String type, int kilometers, int dailyCost) {
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.kilometers = kilometers;
        this.dailyCost = dailyCost;
    }


    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setKilometers(int kilometers) {
        this.kilometers = kilometers;
    }

    public int getKilometers() {
        return kilometers;
    }

    public void setDailyCost(int dailyCost) {
        this.dailyCost = dailyCost;
    }

    public int getDailyCost() {
        return dailyCost;
    }

}

