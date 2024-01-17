package servlets;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import repositories.Database;


@WebServlet("/income")
public class Income  extends HttpServlet{
    Database db = new Database();
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            String startDateStr = request.getParameter("start_date");
            String endDateStr = request.getParameter("end_date");
            String type = request.getParameter("type");
            LocalDate lendDate = LocalDate.now();
            if (endDateStr != null) {
                lendDate = LocalDate.parse(endDateStr, formatter);
            }
            LocalDate lstartDate = LocalDate.parse(startDateStr, formatter);

            Date startDate = Date.valueOf(lstartDate);
            Date endDate = Date.valueOf(lendDate);

            String result = db.income(startDate, endDate, type);

            response.getWriter().write(result);
        }catch (Exception e) {
            response.getWriter().write("Error processing request.");
        }
    }

}
