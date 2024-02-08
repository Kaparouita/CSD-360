package cmd;

import repositories.Database;
import models.Motorcycle;
import models.User;
import models.Rent;

import java.sql.Timestamp;
import java.sql.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Initialize database
        Database db = new Database();
        db.connect();
        db.initializeDatabase();

       db.initDbWithRandomData();

    }

}

