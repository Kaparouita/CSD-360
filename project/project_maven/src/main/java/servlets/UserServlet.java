package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.BufferedReader;
import com.google.gson.Gson;
import models.User;
import repositories.Database;



@WebServlet("/user")
public class UserServlet extends HttpServlet {
    Database db = new Database();
    

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

        Gson gson = new Gson();
        User user = gson.fromJson(jsonData, User.class);

        // Now, you have a User object populated with JSON data
        System.out.println("Registering user: Name = " + user.getFirstName() + ", Email = " + user.getEmail());

        // Save user to database, etc.
        db.saveUser(user);


        response.setContentType("application/json");
        response.getWriter().println(gson.toJson(user));
    }


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        
        Gson gson = new Gson();
        User user = db.getUser(id);
        String json = gson.toJson(user);

        response.setContentType("application/json");
        response.getWriter().println(json);
    }

}
