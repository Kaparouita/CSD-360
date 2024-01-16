package servlets;


import java.sql.Timestamp;
import java.time.LocalDate;
import java.sql.Date;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import repositories.Database;
import models.Rent;

@WebServlet("/rentCalendar")
public class RentCalendar extends HttpServlet {
    Database db = new Database();
    Gson gson = new GsonBuilder()
    .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    .create();

    @Override
    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
    try{
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String startDateStr = request.getParameter("start_date");
        String endDateStr = request.getParameter("end_date");
        LocalDate lendDate = LocalDate.now();
        if (endDateStr != null) {
            lendDate = LocalDate.parse(endDateStr, formatter);
        }
        LocalDate lstartDate = LocalDate.parse(startDateStr, formatter);

        Date startDate = Date.valueOf(lstartDate);
        Date endDate = Date.valueOf(lendDate);

        response.getWriter().write(gson.toJson(db.rentCalendarList(startDate, endDate)));

    } catch (DateTimeParseException e) {
            // Handle invalid date format
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().write("Invalid date format. Please use yyyy-MM-dd.");
    } catch (Exception e) {
        // Handle other exceptions
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().write("Error processing request.");
    }
    }
}
