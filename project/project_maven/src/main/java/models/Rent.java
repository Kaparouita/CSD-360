package models;

import java.sql.Timestamp;

public class Rent {
    private int id;
    private int vehicle_id;
    private int user_id;
    private Timestamp rentDate;
    private Timestamp returnDate;
    private String rentStatus;
    private Boolean ensurance = false;
    private int totalCost;

    public Rent( int vehicle_id,int user_id, Timestamp rentDate, Timestamp returnDate, String rentStatus, Boolean ensurance, int totalCost) {
        this.vehicle_id = vehicle_id;
        this.user_id = user_id;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentStatus = rentStatus;
        this.ensurance = ensurance;
        this.totalCost = totalCost;
    }


    public Boolean getEnsurance() {
        return ensurance;
    }

    public void setEnsurance(Boolean ensurance) {
        this.ensurance = ensurance;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public int getVehicleId() {
        return vehicle_id;
    }
    
    public void setVehicleId(int vehicle_id) {
        this.vehicle_id = vehicle_id;
    }
    
    public int getUserId() {
        return user_id;
    }
    
    public void setUserId(int userId) {
        this.user_id= userId;
    }
    
    public Timestamp getRentDate() {
        return rentDate;
    }

    public void setRentDate(Timestamp rentDate) {
        this.rentDate = rentDate;
    }

    public Timestamp getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Timestamp returnDate) { this.returnDate = returnDate; }

    public String getRentStatus() {
        return rentStatus;
    }
    
    public void setRentStatus(String rentStatus) {
        this.rentStatus = rentStatus;
    }
}
