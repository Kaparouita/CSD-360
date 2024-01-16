package servlets;

import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import javax.xml.crypto.Data;

import java.io.IOException;
import java.io.BufferedReader;
import models.Vehicle;
import repositories.Database;
import models.Car;
import models.Motorcycle;
import models.Scooter;
import models.Bicycle;

@WebServlet("/vehicle")
public class VehicleServlet extends HttpServlet {
    Database db = new Database();
    private Gson gson = new Gson();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder buffer = new StringBuilder();
        String line;
        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
        }

        JsonObject jsonObject = JsonParser.parseString(buffer.toString()).getAsJsonObject();
        String vehicleType = jsonObject.get("type").getAsString();

        Vehicle vehicle;
        switch (vehicleType) {
            case "car":
                vehicle = gson.fromJson(jsonObject, Car.class);
                break;
            case "motorcycle":
                vehicle = gson.fromJson(jsonObject, Motorcycle.class);
                break;
            case "bicycle":
                vehicle = gson.fromJson(jsonObject, Bicycle.class);
                break;
            case "scooter":
                vehicle = gson.fromJson(jsonObject, Scooter.class);
                break;
            default:
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle type");
                return;
        }

        db.saveVehicle(vehicle);
        response.getWriter().write("Vehicle of type " + vehicleType + " created successfully");
        System.out.println("Vehicle " + vehicle + " created successfully");
    }

}
