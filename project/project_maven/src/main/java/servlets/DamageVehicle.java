package servlets;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


import repositories.Database;

@WebServlet("/damage")
public class DamageVehicle extends HttpServlet{
    Database db = new Database();
  

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        
        int rentId = Integer.parseInt(request.getParameter("rent_id"));
        if (rentId == 0) {
            response.getWriter().write("Error: Invalid rent ID");
            return;
        }

        System.out.println("Changing damaged vehicle: RentId = " + rentId );

        db.damageVehicle(rentId);

        response.getWriter().write("Changed damaged vehicle: RentId = " + rentId );
    }

}
