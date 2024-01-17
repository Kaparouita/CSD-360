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

//       db.initDbWithRandomData();

//        List<Motorcycle> motorcycleList =  db.getMotorcycleList(true);
//        for (Motorcycle motorcycle : motorcycleList) {
//            System.out.printf("%d : %b", motorcycle.getId(), motorcycle.getIsRented());
//            System.out.println();
    System.out.println( db.income(Date.valueOf("2020-01-01"), Date.valueOf("2024-12-31"), "car"));
    }

}

