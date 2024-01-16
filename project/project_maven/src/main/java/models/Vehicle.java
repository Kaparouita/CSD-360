package models;


import java.sql.Timestamp;

public abstract class Vehicle {
    
    private int id;
    private String color;
    private String brand;
    private String model;
    private String type; // car, truck, motorcycle, etc.  
    private int kilometers;
    private int dailyCost;
    private Boolean isRented;
    private String status; // damaged, in repair, etc.
    
    public Vehicle(String color,  String brand, String model, String type, int kilometers, int dailyCost ,Boolean isRented, String status) {
        this.color = color;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.kilometers = kilometers;
        this.dailyCost = dailyCost;
        this.isRented = isRented;
        this.status = status;
    }


    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public void setIsRented(Boolean isRented) {
        this.isRented = isRented;
    }

    public Boolean getIsRented() {
        return isRented;
    }
}

