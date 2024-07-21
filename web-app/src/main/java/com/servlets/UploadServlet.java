package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@WebServlet(name = "UploadServlet", urlPatterns = {"/UploadServlet"})
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 1000, // 1 GB
        maxRequestSize = 1024 * 1024 * 1000) // 1 GB
public class UploadServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private PrintWriter out;
    private Connection con;
    private PreparedStatement ps;
    private HttpSession session;

    private String dbURL;
    private String dbUser;
    private String dbPassword;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        dbURL = config.getServletContext().getInitParameter("dbURL");
        dbUser = config.getServletContext().getInitParameter("dbUser");
        dbPassword = config.getServletContext().getInitParameter("dbPassword");
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new ServletException("Failed to load MySQL JDBC driver", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        out = response.getWriter();
        session = request.getSession(false);

        String folderName = "resources";
        String uploadPath = request.getServletContext().getRealPath("") + File.separator + folderName;
        File dir = new File(uploadPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        Part filePart = request.getPart("file");
        String semester = request.getParameter("semester");
        String subject = request.getParameter("subject");
        String fileName = filePart.getSubmittedFileName();
        String path = folderName + File.separator + fileName;
        Timestamp added_date = new Timestamp(System.currentTimeMillis());

        try (InputStream is = filePart.getInputStream()) {
            Files.copy(is, Paths.get(uploadPath + File.separator + fileName), StandardCopyOption.REPLACE_EXISTING);
        }

        try {
            con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
            String sql = "INSERT INTO employee (semester, subject, filename, path, added_date) VALUES (?, ?, ?, ?, ?)";
            ps = con.prepareStatement(sql);
            ps.setString(1, semester);
            ps.setString(2, subject);
            ps.setString(3, fileName);
            ps.setString(4, path);
            ps.setTimestamp(5, added_date);

            int status = ps.executeUpdate();
            if (status > 0) {
                session.setAttribute("fileName", fileName);
                String msg = fileName + " File uploaded successfully...";
                request.setAttribute("msg", msg);
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }
        } catch (SQLException e) {
            out.println("Exception: " + e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                out.println(e);
            }
        }
    }
}
