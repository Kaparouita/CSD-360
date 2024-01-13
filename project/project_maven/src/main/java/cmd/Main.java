package cmd;

import repositories.Database;
import models.Motorcycle;

public class Main {

    public static void main(String[] args) {
        // Initialize database
        Database db = new Database();
        db.connect();
        db.initializeDatabase();

        // Create a new motorcycle
        Motorcycle motorcycle = new Motorcycle("blue","brand", "CBR500R","AA1231", 5000, 50);
        
        // Save the motorcycle to the database
        db.saveVehicle(motorcycle);
    }

}

