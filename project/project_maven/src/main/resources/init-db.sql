CREATE TABLE IF NOT EXISTS Users (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(100),
    password VARCHAR(100),
    email VARCHAR(100),
    firstName VARCHAR(100),
    lastName VARCHAR(100),
    address VARCHAR(100),
    phoneNumber VARCHAR(100),
    driverLicense VARCHAR(100),
    creditCardNumber VARCHAR(100),
    age INT,
    rent_ids INT[]
);
CREATE TABLE IF NOT EXISTS Vehicle (
    vehicle_id SERIAL PRIMARY KEY,
    brand VARCHAR(255),
    model VARCHAR(255),
    kilometers INT,
    color VARCHAR(255),
    type VARCHAR(20),
    dailyCost INT,
    isRented BOOLEAN,
    status VARCHAR(20)
);
CREATE TABLE IF NOT EXISTS Car(
    vehicle_id INT PRIMARY KEY,
    licensePlate VARCHAR(50),
    carType VARCHAR(50),
    numberOfSeats INT,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE IF NOT EXISTS Motorcycle (
    vehicle_id INT PRIMARY KEY,
    licensePlate VARCHAR(50),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE IF NOT EXISTS Bicycle (
    vehicle_id INT PRIMARY KEY,
    uniqueNumber VARCHAR(10),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE IF NOT EXISTS Scooter (
    vehicle_id INT PRIMARY KEY,
    uniqueNumber VARCHAR(10),
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id)
);
CREATE TABLE IF NOT EXISTS Rental (
    rent_id SERIAL PRIMARY KEY,
    vehicle_id INT,
    user_id INT,
    rentDate TIMESTAMP,
    returnDate TIMESTAMP,
    rentStatus VARCHAR(50),
    ensurance BOOLEAN,
    totalCost INT,
    FOREIGN KEY (vehicle_id) REFERENCES Vehicle(vehicle_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id) 
);