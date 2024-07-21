//package com.servlets;
//
//import java.io.IOException;
//import java.io.PrintWriter;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@WebServlet(name = "FetchFilesServlet", urlPatterns = {"/FetchFilesServlet"})
//public class FetchFilesServlet extends HttpServlet {
//
//    private static final long serialVersionUID = 1L;
//
//    private String dbURL;
//    private String dbUser;
//    private String dbPassword;
//
//    @Override
//    public void init() throws ServletException {
//        dbURL = getServletContext().getInitParameter("dbURL");
//        dbUser = getServletContext().getInitParameter("dbUser");
//        dbPassword = getServletContext().getInitParameter("dbPassword");
//        
//        try {
//            Class.forName("com.mysql.cj.jdbc.Driver");
//        } catch (ClassNotFoundException e) {
//            throw new ServletException("Failed to load MySQL JDBC driver", e);
//        }
//    }
//
//    @Override
//    protected void doGet(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        response.setContentType("application/json");
//        PrintWriter out = response.getWriter();
//        
//        String subject = request.getParameter("subject");
//        List<String> fileNames = new ArrayList<>();
//
//        try (Connection con = DriverManager.getConnection(dbURL, dbUser, dbPassword)) {
//            String sql = "SELECT filename FROM employee WHERE subject = ?";
//            try (PreparedStatement ps = con.prepareStatement(sql)) {
//                ps.setString(1, subject);
//                try (ResultSet rs = ps.executeQuery()) {
//                    while (rs.next()) {
//                        fileNames.add(rs.getString("filename"));
//                    }
//                }
//            }
//            out.println(fileNames.toString());
//        } catch (SQLException e) {
//            out.println("{\"error\": \"" + e.getMessage() + "\"}");
//        }
//    }
//}
