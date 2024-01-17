package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import repositories.Database;


@WebServlet("/return")
public class ReturnVehicle extends HttpServlet {
    Database db = new Database();


    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {

            int rentId = Integer.parseInt(request.getParameter("rent_id"));
            if (rentId == 0) {
                response.getWriter().write("Error: Invalid rent ID");
                return;
            }

            System.out.println("Returning vehicle: RentId = " + rentId);

            db.returnVehicle(rentId);

            response.getWriter().write("Returned vehicle: RentId = " + rentId);
        }catch (Exception e) {
            response.getWriter().write("Error processing request.");
        }
    }
    
}
