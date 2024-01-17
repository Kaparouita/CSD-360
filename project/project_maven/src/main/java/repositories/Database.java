package repositories;

import cmd.Main;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.io.IOException;
import java.sql.Date;

import models.User;
import models.Vehicle;
import models.Rent;
import models.Bicycle;
import models.Car;
import models.Motorcycle;
import models.Scooter;
import java.sql.Timestamp;


public class Database {
    private static final String URL = "jdbc:postgresql://localhost:5432/360";
    private static final String USER = "maou";
    private static final String PASSWORD = "maou";
    private Connection db;


    public Database() {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Error: Unable to load PostgreSQL Driver");
            // Handle exceptions
        }
    }
   

    public Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Error: Unable to connect to database");
            // Handle exceptions
        }
        this.db = conn;
        return conn;
    }


    public void initializeDatabase() {
        try (InputStream inputStream = getClass().getResourceAsStream("/init-db.sql");
             BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
             Statement statement = this.db.createStatement()) {

            String query = reader.lines().collect(Collectors.joining(" "));
            String[] queries = query.split(";");

            for (String q : queries) {
                statement.execute(q);
            }

        } catch (IOException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void initDbWithRandomData(){
        saveUser(new User("username", "password", "email", "firstname", "lastname", "address", "phonenumber", "driverlicense", "creditcardnumber",20));
       
       for (int i = 0; i < 3; i++) {
            saveVehicle(new Motorcycle("color", "brand", "model", "licensePlate", 0, 40, false, "available"));
            saveVehicle(new Car("color", "brand", "model", "carType", "car", 0, "licensePlate", 0, 60, false, "available"));
            saveVehicle(new Bicycle("color", "brand", "model", "bicycle", "uni543mr", 0, 10, false, "available"));
            saveVehicle(new Scooter("color", "brand", "model", "scooter", "uni543mer", 0, 15, false, "available"));
        }
        for (int i = 0; i < 3; i++) {
            saveVehicle(new Motorcycle("color", "brand", "model", "licensePlate", 0, 25, true,"damaged"));
            saveVehicle(new Car("color", "brand", "model", "carType", "car", 0, "licensePlate", 0, 30, true,"available"));
            saveVehicle(new Bicycle("color", "brand", "model", "bicycle", "uniq432", 0, 5, true,"available"));
            saveVehicle(new Scooter("color", "brand", "model", "scooter", "uniqu423", 0, 5, true,"available"));
        }
    }
    

    public User saveUser(User user) {
        String SQL = "INSERT INTO users(username, password, email, firstname, lastname, address, phonenumber, driverlicense, creditcardnumber,age) VALUES(?,?,?,?,?,?,?,?,?,?)";

        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getEmail());
            pstmt.setString(4, user.getFirstName());
            pstmt.setString(5, user.getLastName());
            pstmt.setString(6, user.getAddress());
            pstmt.setString(7, user.getPhoneNumber());
            pstmt.setString(8, user.getDriverLicense());
            pstmt.setString(9, user.getCreditCardNumber());
            pstmt.setInt(10, user.getAge());
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                int generatedId = rs.getInt(1);
                user.setId(generatedId);
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return user;
    }

    public User getUser(int id){
        String SQL = "SELECT * FROM users WHERE user_id = ?";
        User user = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = new User(
                    rs.getString("username"),
                    rs.getString("password"),
                    rs.getString("email"),
                    rs.getString("firstname"),
                    rs.getString("lastname"),
                    rs.getString("address"),
                    rs.getString("phonenumber"),
                    rs.getString("driverlicense"),
                    rs.getString("creditcardnumber"),
                    rs.getInt("age")
                );
                user.setId(rs.getInt("user_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
        return user;
    }


    public void saveVehicle(Vehicle vehicle) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet generatedKeys = null;

        try {
            conn = connect();

            // Common fields insertion into Vehicle table
            String vehicleSQL = "INSERT INTO Vehicle(color,brand,model,type,kilometers,dailyCost,isRented,status) VALUES(?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(vehicleSQL, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString(1, vehicle.getColor());
            pstmt.setString(2, vehicle.getBrand());
            pstmt.setString(3, vehicle.getModel());
            pstmt.setString(4, vehicle.getType());
            pstmt.setInt(5, vehicle.getKilometers());
            pstmt.setInt(6, vehicle.getDailyCost());
            pstmt.setBoolean(7, vehicle.getIsRented());
            pstmt.setString(8, vehicle.getStatus());


            int affectedRows = pstmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating vehicle failed, no rows affected.");
            }

            // Retrieve generated vehicle_id
            generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int vehicleId = generatedKeys.getInt(1);

                // Call type-specific saving method
                if (vehicle instanceof Motorcycle) {
                    saveMotorcycle((Motorcycle) vehicle, vehicleId);
                } else if (vehicle instanceof Car) {
                    saveCar((Car) vehicle, vehicleId);
                } else if (vehicle instanceof Bicycle) {
                    saveBicycle((Bicycle) vehicle, vehicleId);
                } else if (vehicle instanceof Scooter) {
                    saveScooter((Scooter) vehicle, vehicleId);
                } else {
                    throw new SQLException("Creating vehicle failed, no ID obtained.");
                }
            } else {
                throw new SQLException("Creating vehicle failed, no ID obtained.");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (generatedKeys != null) {
                    generatedKeys.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    

    private void saveMotorcycle(Motorcycle motorcycle, int vehicleId) {
        String SQL = "INSERT INTO motorcycle(vehicle_id,licensePlate) VALUES(?,?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setString(2, motorcycle.getLicensePlate());


            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveCar(Car car, int vehicleId) {
        String SQL = "INSERT INTO car(vehicle_id,numberOfSeats,licensePlate,carType) VALUES(?,?,?,?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setInt(2, car.getNumberOfSeats());
            pstmt.setString(3, car.getLicensePlate());
            pstmt.setString(4, car.getCarType());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveBicycle(Bicycle bicycle, int vehicleId) {
        String SQL = "INSERT INTO bicycle(vehicle_id,uniqueNumber) VALUES(?,?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setString(2, bicycle.getUniqueNumber());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    private void saveScooter(Scooter scooter, int vehicleId) {
        String SQL = "INSERT INTO scooter(vehicle_id,uniqueNumber) VALUES(?,?)";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicleId);
            pstmt.setString(2, scooter.getUniqueNumber());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List<Car> getCarList(Boolean isRented) {
        List<Car> cars = new ArrayList<>();
        String SQL = "SELECT * FROM vehicle INNER JOIN car ON vehicle.vehicle_id = car.vehicle_id";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Car car = new Car(
                        rs.getString("color"),
                        rs.getString("brand"),
                        rs.getString("model"),
                        rs.getString("carType"),
                        rs.getString("type"),
                        rs.getInt("numberOfSeats"),
                        rs.getString("licensePlate"),
                        rs.getInt("kilometers"),
                        rs.getInt("dailyCost"),
                        rs.getBoolean("isRented"),
                        rs.getString("status")
                );
                car.setId(rs.getInt("vehicle_id"));
                if ((isRented && car.getIsRented() )|| (!isRented && !car.getIsRented())){
                    cars.add(car);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return cars;
    }

    public List<Motorcycle> getMotorcycleList(Boolean isRented) {
        List<Motorcycle> motorcycles = new ArrayList<>();
        String SQL = "SELECT * FROM vehicle INNER JOIN motorcycle ON vehicle.vehicle_id = motorcycle.vehicle_id";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Motorcycle motorcycle = new Motorcycle(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("licensePlate"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                motorcycle.setId(rs.getInt("vehicle_id"));
                if ((isRented && motorcycle.getIsRented() ) || (!isRented && !motorcycle.getIsRented())) {
                    motorcycles.add(motorcycle);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return motorcycles;
    }

    public List<Bicycle> getBicycleList(Boolean isRented) {
        List<Bicycle> bicycles = new ArrayList<>();
        String SQL = "SELECT * FROM vehicle INNER JOIN bicycle ON vehicle.vehicle_id = bicycle.vehicle_id";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Bicycle bicycle = new Bicycle(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("type"),
                    rs.getString("uniqueNumber"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                bicycle.setId(rs.getInt("vehicle_id"));
                if ((isRented && bicycle.getIsRented() )|| (!isRented && !bicycle.getIsRented()))
                    bicycles.add(bicycle);            
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return bicycles;
    }

    public List<Scooter> getScooterList(Boolean isRented) {
        List<Scooter> scooters = new ArrayList<>();
        String SQL = "SELECT * FROM vehicle INNER JOIN scooter ON vehicle.vehicle_id = scooter.vehicle_id";

        try (Connection conn = connect();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(SQL)) {

            while (rs.next()) {
                Scooter scooter = new Scooter(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("type"),
                    rs.getString("uniqueNumber"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                scooter.setId(rs.getInt("vehicle_id"));
                if ((isRented && scooter.getIsRented() )|| (!isRented && !scooter.getIsRented()))
                    scooters.add(scooter);            
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return scooters;
    }

    public String rentVehicle(Rent rent) {
        String SQL = "INSERT INTO rental(user_id,vehicle_id,rentDate,returnDate,rentStatus,ensurance,totalCost) VALUES(?,?,?,?,?,?,?)";
        String SQLvehicle = "UPDATE vehicle SET isRented = true WHERE vehicle_id = ?";
        String SQLgetVehicle = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        String resp= "";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            PreparedStatement pstmtvehicle = conn.prepareStatement(SQLvehicle);
            PreparedStatement pstmtgetVehicle = conn.prepareStatement(SQLgetVehicle)) {
            pstmt.setInt(1, rent.getUserId());
            pstmt.setInt(2, rent.getVehicleId());
            pstmt.setTimestamp(3, rent.getRentDate());
            pstmt.setTimestamp(4, rent.getReturnDate());
            pstmt.setString(5, "rented");
            pstmt.setBoolean(6, rent.getEnsurance());

            Vehicle vehicle = getVehicle(rent.getVehicleId());
            User user = getUser(rent.getUserId());
            
            if((vehicle instanceof Car || vehicle instanceof Motorcycle )&& (user.getAge() < 18 || user.getDriverLicense() == null)){
                return "You are not allowed to rent a car or motorcycle";
            }else if (user.getAge() < 16){
                return "You are not allowed to rent a bicycle or scooter";
            }else if (user.getCreditCardNumber() == null){
                return "You need to add a credit card to rent a vehicle";
            }

            pstmtvehicle.setInt(1, rent.getVehicleId());
            

            pstmtgetVehicle.setInt(1, rent.getVehicleId());
            ResultSet rs = pstmtgetVehicle.executeQuery();
            if (rs.next()) {
                if(rs.getString("status").equals("damaged")){
                    return "Vehicle is damaged";
                }else if (rs.getBoolean("isRented")){
                    return "Vehicle is in rented";
                }

                int dailyCost = rs.getInt("dailyCost");
                int totalFee = calculateFee(rent.getRentDate(), rent.getReturnDate(), dailyCost);
                if (rent.getEnsurance()) {
                    totalFee += 100; // ensurance fee
                }
                //!TODO : print total fee
                resp += "Total fee: " + totalFee+"\n";
                System.out.println("Total fee: " + totalFee);
                pstmt.setInt(7, totalFee);
            }

            pstmtvehicle.executeUpdate();
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        resp += "Rent successful!";
        return resp;
    }

    private int calculateFee(Timestamp rentDateTimestamp, Timestamp returnDateTimestamp, int dailyCost){
        LocalDate rentDate = rentDateTimestamp.toLocalDateTime().toLocalDate();
        LocalDate returnDate = returnDateTimestamp.toLocalDateTime().toLocalDate();
        long daysBetween = ChronoUnit.DAYS.between(rentDate, returnDate);
        int totalFee = (int) daysBetween * dailyCost;
        return totalFee;
    }

    public String returnVehicle(int rent_id){
        String SQLrent = "SELECT * FROM rental WHERE rent_id = ? AND rentStatus = 'rented'";
        String SQLvehicle = "UPDATE vehicle SET isRented = false WHERE vehicle_id = ? ";
        String SQL = "UPDATE rental SET rentStatus = 'returned', totalCost = ? WHERE rent_id = ?";
        String resp = "";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL);
            PreparedStatement pstmtrent = conn.prepareStatement(SQLrent);
            PreparedStatement pstmtvehicle = conn.prepareStatement(SQLvehicle)) {
            pstmtrent.setInt(1, rent_id);
            ResultSet rent = pstmtrent.executeQuery();
            if (rent.next()) {
                int vehicle_id = rent.getInt("vehicle_id");
                Timestamp returnDate = rent.getTimestamp("returnDate");
                
                Timestamp currTime = new Timestamp(System.currentTimeMillis());
                int exccedTime = exccedTime(currTime, returnDate);


                if (exccedTime < 0) {
                    int extrafee = exccedTime * 10;
                    System.out.println("The extra fee for late return is : " + extrafee+"  late for "+ -1*exccedTime+" hours");
                    resp = "The extra fee for late return is : " + extrafee+"  late for "+ -1*exccedTime+" hours\n";
                    pstmt.setInt(1, rent.getInt("totalCost") + extrafee);
                }
                pstmtvehicle.setInt(1, vehicle_id);
                pstmtvehicle.executeUpdate();
                
                pstmt.setInt(2, rent_id);
                pstmt.executeUpdate();
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        resp += "Vehicle returned successfully!";
        return resp;
    }

    private int exccedTime(Timestamp currDate, Timestamp returnDate){
        long diff = returnDate.getTime() - currDate.getTime();
        long diffHours = diff / (60 * 60 * 1000) % 24;
        return (int) diffHours;
    }

    public void damageVehicle(int rent_id){
        String SQLrent = "SELECT * FROM rental WHERE rent_id = ? AND rentStatus = 'rented'";
        String SQLupdateVehicle = "UPDATE vehicle SET status = 'damaged',isRented = false WHERE vehicle_id = ?";
        String SQLgetVehicle = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        String SQLsimilarVehicle = "SELECT * FROM vehicle WHERE type = ? AND isRented = false AND status = 'available' LIMIT 1";
        String SQLreplaceVehicle = "UPDATE rental SET vehicle_id = ? WHERE rent_id = ?";

        try (Connection conn = connect();
            PreparedStatement pstmtvehicle = conn.prepareStatement(SQLupdateVehicle);
            PreparedStatement pstmtrent = conn.prepareStatement(SQLrent);
            PreparedStatement pstmtgetVehicle = conn.prepareStatement(SQLgetVehicle);
            PreparedStatement pstmtsimilarVehicle = conn.prepareStatement(SQLsimilarVehicle);
            PreparedStatement pstmtreplaceVehicle = conn.prepareStatement(SQLreplaceVehicle)) {
            pstmtrent.setInt(1, rent_id);
            ResultSet rent = pstmtrent.executeQuery();
            if (rent.next()) {
                pstmtvehicle.setInt(1, rent.getInt("vehicle_id"));
                pstmtvehicle.executeUpdate();

                pstmtgetVehicle.setInt(1, rent.getInt("vehicle_id"));
                ResultSet veh = pstmtgetVehicle.executeQuery();
                veh.next();

                pstmtsimilarVehicle.setString(1, veh.getString("type"));
                ResultSet rs2 = pstmtsimilarVehicle.executeQuery();
                if (rs2.next()) {
                    pstmtreplaceVehicle.setInt(1, rs2.getInt("vehicle_id"));
                    pstmtreplaceVehicle.setInt(2, rent_id);
                    pstmtreplaceVehicle.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void accidentVehicle(int rent_id){
        String SQLrent = "SELECT * FROM rental WHERE rent_id = ? AND rentStatus = 'rented'";
        String SQLtotalCost = "UPDATE rental SET totalCost = ? WHERE rent_id = ?";


        try (Connection conn = connect();
            PreparedStatement pstmtrent = conn.prepareStatement(SQLrent);
            PreparedStatement pstmttotalCost = conn.prepareStatement(SQLtotalCost)) {
            pstmtrent.setInt(1, rent_id);
            ResultSet rent = pstmtrent.executeQuery();
            if (rent.next()) {
                if (rent.getBoolean("ensurance")) {
                    damageVehicle(rent_id);
                } else {
                    System.out.println("User doesnt have ensurance!");
                    int totalCost = rent.getInt("totalCost") * 3;
                    pstmttotalCost.setInt(1, totalCost);
                    pstmttotalCost.setInt(2, rent_id);
                    pstmttotalCost.executeUpdate();
                    damageVehicle(rent_id);
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public List <Rent> rentCalendarList(Date start_date,Date end_date){
        List<Rent> rents = new ArrayList<>();
        String SQL = "SELECT * FROM rental WHERE rentDate >= ? AND returnDate <= ?";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setDate(1, start_date);
            pstmt.setDate(2, end_date);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Rent rent = new Rent(
                    rs.getInt("vehicle_id"),
                    rs.getInt("user_id"),
                    rs.getTimestamp("rentDate"),
                    rs.getTimestamp("returnDate"),
                    rs.getString("rentStatus"),
                    rs.getBoolean("ensurance"),
                    rs.getInt("totalCost")
                );
                rent.setId(rs.getInt("rent_id"));
                rents.add(rent);
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return rents;
    }

    public String calculateMinRentTime(String vehicleType) {
        String minRentTime = "";
        String SQL = "SELECT MIN(returnDate - rentDate) AS minRentTime FROM rental WHERE vehicle_id IN (SELECT vehicle_id FROM " + vehicleType + ")";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                minRentTime = rs.getString("minRentTime");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return minRentTime;
    }

    public String calculateMaxRentTime(String vehicleType) {
        String maxRentTime="";
        String SQL = "SELECT MAX(returnDate - rentDate) AS maxRentTime FROM rental WHERE vehicle_id IN (SELECT vehicle_id FROM " + vehicleType + ")";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                maxRentTime = rs.getString("maxRentTime");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return maxRentTime;
    }

    public String calculateAverageRentTime(String vehicleType) {
        String averageRentTime = "";
        String SQL = "SELECT AVG(returnDate - rentDate) AS averageRentTime FROM rental WHERE vehicle_id IN (SELECT vehicle_id FROM " + vehicleType + ")";

        try (Connection conn = connect();
            PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                averageRentTime = rs.getString("averageRentTime");
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return averageRentTime;
    }

    public Vehicle getVehicle(int vehicle_id){
        String SQL = "SELECT * FROM vehicle WHERE vehicle_id = ?";
        Vehicle vehicle = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicle_id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                switch (rs.getString("type")) {
                    case "car":
                        vehicle = getCar(vehicle_id);
                        break;
                    case "motorcycle":
                        vehicle = getMotorcycle(vehicle_id);
                        break;
                    case "bicycle":
                        vehicle = getBicycle(vehicle_id);
                        break;
                    case "scooter":
                        vehicle = getScooter(vehicle_id);
                        break;
                    default:
                        System.out.println("Error: Invalid vehicle type");
                        break;
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return vehicle;
    }

    private Car getCar(int vehicle_id){
        String SQL = "SELECT * FROM vehicle INNER JOIN car ON vehicle.vehicle_id = car.vehicle_id WHERE vehicle.vehicle_id = ?";
        Car car = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicle_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                car = new Car(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("carType"),
                    rs.getString("type"),
                    rs.getInt("numberOfSeats"),
                    rs.getString("licensePlate"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                car.setId(rs.getInt("vehicle_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return car;
    }

    private Motorcycle getMotorcycle(int vehicle_id){
        String SQL = "SELECT * FROM vehicle INNER JOIN motorcycle ON vehicle.vehicle_id = motorcycle.vehicle_id WHERE vehicle.vehicle_id = ?";
        Motorcycle motorcycle = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicle_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                motorcycle = new Motorcycle(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("licensePlate"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                motorcycle.setId(rs.getInt("vehicle_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return motorcycle;
    }

    private Bicycle getBicycle(int vehicle_id){
        String SQL = "SELECT * FROM vehicle INNER JOIN bicycle ON vehicle.vehicle_id = bicycle.vehicle_id WHERE vehicle.vehicle_id = ?";
        Bicycle bicycle = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicle_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bicycle = new Bicycle(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("type"),
                    rs.getString("uniqueNumber"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                bicycle.setId(rs.getInt("vehicle_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return bicycle;
    }

    private Scooter getScooter(int vehicle_id){
        String SQL = "SELECT * FROM vehicle INNER JOIN scooter ON vehicle.vehicle_id = scooter.vehicle_id WHERE vehicle.vehicle_id = ?";
        Scooter scooter = null;
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setInt(1, vehicle_id);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                scooter = new Scooter(
                    rs.getString("color"),
                    rs.getString("brand"),
                    rs.getString("model"),
                    rs.getString("type"),
                    rs.getString("uniqueNumber"),
                    rs.getInt("kilometers"),
                    rs.getInt("dailyCost"),
                    rs.getBoolean("isRented"),
                    rs.getString("status")
                );
                scooter.setId(rs.getInt("vehicle_id"));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return scooter;
    }

    public String income(Date starDate,Date endDate,String type){
        String SQL = "SELECT SUM(totalCost) AS income FROM rental WHERE rentDate >= ? AND returnDate <= ? AND rentStatus = 'returned' AND vehicle_id IN (SELECT vehicle_id FROM "+type+")";
        String income = "";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            pstmt.setDate(1, starDate);
            pstmt.setDate(2, endDate);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                income = rs.getString("income");
                if (income == null) {
                    income = "0";
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "Error";
        }
        return income;
    }

    public String mostRentedVehicle(String type){
        String SQL = "SELECT vehicle_id,COUNT(vehicle_id) AS count FROM rental WHERE vehicle_id IN (SELECT vehicle_id FROM "+type+") GROUP BY vehicle_id ORDER BY count DESC LIMIT 1";
        String mostRentedVehicle = "";
        try (Connection conn = connect();
             PreparedStatement pstmt = conn.prepareStatement(SQL)) {
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                mostRentedVehicle = rs.getString("vehicle_id");
                if (mostRentedVehicle == null) {
                    mostRentedVehicle = "0";
                }
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return "Error";
        }
        return mostRentedVehicle;
    }
}

