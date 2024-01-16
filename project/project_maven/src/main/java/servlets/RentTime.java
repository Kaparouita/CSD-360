package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import com.google.gson.Gson;

import repositories.Database;

@WebServlet("/rentTime")
public class RentTime extends HttpServlet{
    Database db = new Database();
    Gson gson = new Gson();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try{
        String type = request.getParameter("type");

        if (type == null) {
            response.getWriter().write("Error: Missing vehicle type");
            return;
        }

        // Retrieve max, min, and avg time values from the database
        String maxTime = db.calculateMaxRentTime(type);
        String minTime = db.calculateMinRentTime(type);
        String avgTime = db.calculateAverageRentTime(type);

        // Use the retrieved values as needed
        // For example, you can write them to the response
        response.getWriter().write("Max Time: " + maxTime + ",\n Min Time: " + minTime + ",\n Avg Time: " + avgTime);
         } catch (Exception e) {
            response.getWriter().write("Error: " + e.getMessage());
    }
}
}
