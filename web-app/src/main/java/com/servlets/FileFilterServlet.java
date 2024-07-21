package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/FileFilterServlet")
public class FileFilterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String dbURL;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbURL = config.getServletContext().getInitParameter("dbURL");
        dbUser = config.getServletContext().getInitParameter("dbUser");
        dbPassword = config.getServletContext().getInitParameter("dbPassword");
        
        // Ensure JDBC driver is loaded
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to load MySQL JDBC driver", e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedSemester = request.getParameter("semester");
        String selectedSubject = request.getParameter("subject");

        try (Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
             PreparedStatement ps = con.prepareStatement("SELECT * FROM employee WHERE semester = ? AND subject = ?")) {

            ps.setString(1, selectedSemester);
            ps.setString(2, selectedSubject);
            ResultSet rs = ps.executeQuery();

            request.setAttribute("resultSet", rs);
            request.getRequestDispatcher("file-list.jsp").forward(request, response);

        } catch (SQLException e) {
            throw new ServletException("Database access error", e);
        }
    }
}
