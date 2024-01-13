package servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;


@WebServlet("/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        // Here, you would typically save these details to a database.
        // For demonstration, let's just print them to the server console.
        System.out.println("Registering user: Name = " + name + ", Email = " + email);

        response.setContentType("text/html");
        response.getWriter().println("<p>User " + name + " registered successfully!</p>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.getWriter().println("<p>GET request received!</p>");
    }

}
