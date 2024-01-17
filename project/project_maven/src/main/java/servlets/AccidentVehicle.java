package servlets;


import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import repositories.Database;


@WebServlet("/accident")
public class AccidentVehicle extends HttpServlet{
    Database db = new Database();

    @Override
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        try {
            int rentId = Integer.parseInt(request.getParameter("rent_id"));
            if (rentId == 0) {
                response.getWriter().write("Error: Invalid rent ID");
                return;
            }

            System.out.println("Inform about accident on vehicle: RentId = " + rentId);

            db.accidentVehicle(rentId);

            response.getWriter().write("Inform about accident on vehicle: RentId = " + rentId);
        }catch (Exception e) {
            response.getWriter().write("Error processing request.");
        }
    }
}
