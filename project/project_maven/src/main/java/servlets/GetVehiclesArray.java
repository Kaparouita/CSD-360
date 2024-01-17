package servlets;

import com.google.gson.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.io.IOException;

import repositories.Database;


@WebServlet("/vehicles")
public class GetVehiclesArray  extends HttpServlet{
    Database db = new Database();
    private Gson gson = new Gson();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String vehicleType = request.getParameter("type");
            Boolean isRented = Boolean.parseBoolean(request.getParameter("isRented"));

            if (vehicleType == null) {
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Missing vehicle type");
                return;
            }
            switch (vehicleType) {
                case "car":
                    response.getWriter().write(gson.toJson(db.getCarList(isRented)));
                    break;
                case "motorcycle":
                    response.getWriter().write(gson.toJson(db.getMotorcycleList(isRented)));
                    break;
                case "bicycle":
                    response.getWriter().write(gson.toJson(db.getBicycleList(isRented)));
                    break;
                case "scooter":
                    response.getWriter().write(gson.toJson(db.getScooterList(isRented)));
                    break;
                default:
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid vehicle type");
            }
        }catch (Exception e) {
            response.getWriter().write("Error processing request.");
        }
    }

}
