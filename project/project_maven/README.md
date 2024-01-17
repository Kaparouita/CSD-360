## This readme is about the request and response of the API 

http://localhost:8080/api/ +request

### register User (POST) 
Domain : /user  
Register a new user to the system
```json
{
    "username": "user1",
    "password": "pass1",
    "email": "test@io.gr",
    "firstName": "John",
    "lastName": "Doe",
    "address": "Athens",
    "phoneNumber": "1234567890",
    "driverLicense": "1234567890",
    "creditCardNumber": "1234567890",
    "age": 20
}
```
### get User (GET) 
Domain : /user?{user_id}  
Get a user by id

params: user_id
returns: User

### add Vehicle (POST) 
Domain : /vehicle  
Add a new vehicle to the system
```json
{
        "color": "red",
        "brand": "BMW",
        "model": "X5",
        "type": "car",
        "kilometers": 10000,
        "dailyCost": 100,
        "isRented": false,
        "status": "ok",
        "numberOfSeats": 5,
        "licensePlate": "1234",
        "carType": "SUV"
}
```

### get Vehicle (GET) 
Domain : /vehicle?{vehicle_id}  
Get a vehicle by id

params: vehicle_id
returns: Vehicle

### get Vehicles (GET) 
Domain : /vehicles?{type}  
Get all vehicles of a specific type  

params: type  
returns: List<Vehicle> of type {type}  

### rent Vehicle (POST) 
Domain : /rent  
Rent a vehicle for a specific user  
```json
{
    "vehicleId": 1,
    "userId": 1,
    "startDate": "2020-01-01",
    "endDate": "2020-01-02",
    "rentStatus" : "rented",
    "ensurance" : "true" 
}
```

### return Vehicle (POST) 
Domain : /return  
Return a vehicle  

params: rent_id  

### damage Vehicle (POST) 
Domain : /damage
Report a damage to a vehicle and changed it with a new one  

params: rent_id  

### accident Vehicle (POST) 
Domain : /accident  
Report an accident to a vehicle and changed it with a new one  
if the user has ensurance the cost is 0, else the cost is 3*rentCost  

params: rent_id  

### rent Calendar (GET) 
Domain : /rentCalendar?{startDate}&{endDate}  
Get all rents between two dates,  
if endDate is null then get all rents from startDate to today  

params: startDate, endDate  
returns: List<Rent>  

### rent Time (GET) 
Domain : /rentTime?{type}  
Get the average,min and max rent time of a specific type of vehicle  

params: type  
returns: String  

### income (GET) 
Domain : /income?{startDate}&{endDate}&{type}  
Get the income of a specific type of vehicle between two dates,  
if endDate is null then get all rents from startDate to today  

params: startDate, endDate, type  
returns: String  

### most Rented (GET) 
Domain : /mostRented?{type}  
Get the most rented vehicle of a specific type  

params: type  
returns: vehicle_id  

