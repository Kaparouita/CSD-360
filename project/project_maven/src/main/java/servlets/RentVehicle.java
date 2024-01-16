package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.crypto.Data;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import repositories.Database;

@WebServlet("/rent")
public class RentVehicle extends HttpServlet{
    Database db = new Database();
    private Gson gson = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    .create();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        StringBuilder sb = new StringBuilder();
        String line;

        try (BufferedReader reader = request.getReader()) {
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
        }

        String jsonData = sb.toString();

        models.Rent rent = gson.fromJson(jsonData, models.Rent.class);

        System.out.println("Renting vehicle: VehicleId = " + rent.getVehicleId() + ", UserId = " + rent.getUserId());

        db.rentVehicle(rent);

        response.getWriter().write("Rented vehicle: VehicleId = " + rent.getVehicleId() + ", UserId = " + rent.getUserId());
    }
    

}
