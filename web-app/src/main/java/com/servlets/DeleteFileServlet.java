package com.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class DeleteFileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private String dbURL;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init() throws ServletException {
        dbURL = getServletContext().getInitParameter("dbURL");
        dbUser = getServletContext().getInitParameter("dbUser");
        dbPassword = getServletContext().getInitParameter("dbPassword");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String fileName = request.getParameter("fileName");

        if (fileName != null && !fileName.isEmpty()) {
            Connection con = null;
            PreparedStatement ps = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(dbURL, dbUser, dbPassword);

                String sql = "DELETE FROM employee WHERE filename = ?";
                ps = con.prepareStatement(sql);
                ps.setString(1, fileName);

                int rowsDeleted = ps.executeUpdate();
                if (rowsDeleted > 0) {
                    response.sendRedirect("delete.jsp?status=success");
                } else {
                    response.sendRedirect("delete.jsp?status=error");
                }
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
                response.sendRedirect("delete.jsp?status=error");
            } finally {
                if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
                if (con != null) try { con.close(); } catch (SQLException ignore) {}
            }
        } else {
            response.sendRedirect("delete.jsp?status=invalid");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
