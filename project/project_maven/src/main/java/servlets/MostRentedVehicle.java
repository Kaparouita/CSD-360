package servlets;

import javax.servlet.annotation.WebServlet;
import javax.xml.crypto.Data;
import javax.servlet.http.HttpServlet;

import repositories.Database;


@WebServlet("/mostRentedVehicle")
public class MostRentedVehicle extends HttpServlet{
    Database db = new Database();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
       try{
            String type = request.getParameter("type");
            String result = db.mostRentedVehicle(type);
            response.getWriter().write(result);
       } catch (Exception e) {
            response.getWriter().write("Error processing request.");
        }
    }
}
