package com.servlets;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import jakarta.activation.DataHandler;
import jakarta.activation.DataSource;
import jakarta.activation.FileDataSource;
import jakarta.mail.Message;
import jakarta.mail.Multipart;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeBodyPart;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.internet.MimeMultipart;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
        maxFileSize = 1024 * 1024 * 10, // 10MB
        maxRequestSize = 1024 * 1024 * 50)

@WebServlet(name = "ContactServlet", urlPatterns = {"/ContactServlet"})
public class ContactServlet extends HttpServlet {

    public static final String UPLOAD_DIR = "mail";
    PrintWriter out;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            out = response.getWriter();
            String name = request.getParameter("name");
            String email = request.getParameter("email");
            String post = request.getParameter("post");
            String phone = request.getParameter("phone");
            Part part = request.getPart("resume");
            String fileName = extractFileName(part);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String date = sdf.format(new Date());

            if (!fileName.equals("") || !fileName.isEmpty()) {
                System.out.println("in if");
                String applicationPath = getServletContext().getRealPath("");
                String uploadPath = applicationPath + File.separator + UPLOAD_DIR;
                System.out.println("applicationPath:" + applicationPath);
                File fileUploadDirectory = new File(uploadPath);
                if (!fileUploadDirectory.exists()) {
                    fileUploadDirectory.mkdirs();
                }
                String savePath = uploadPath + File.separator + fileName;
                System.out.println("savePath: " + savePath);
                part.write(savePath + File.separator);
                File fileSaveDir1 = new File(savePath);
                part.write(savePath + File.separator);
                final String username = "jyothinarne07@gmail.com";//your email here
                final String password = "zvymblvgfkdykgor";//your password here
                Properties props = new Properties();
                props.put("mail.smtp.auth", true);
                props.put("mail.smtp.starttls.enable", true);
                props.put("mail.smtp.host", "smtp.gmail.com");
                props.put("mail.smtp.port", "587");
                Session session = Session.getInstance(props, new jakarta.mail.Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username, password);
                    }
                });
                try {
                    Message message = new MimeMessage(session);
                    message.setFrom(new InternetAddress(email));
                    message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username));
                    MimeBodyPart textPart = new MimeBodyPart();
                    MimeBodyPart htmlPart = new MimeBodyPart();
                    MimeBodyPart messageBodyPart = new MimeBodyPart();
                    Multipart multipart = new MimeMultipart();
                    DataSource source = new FileDataSource(savePath);
                    messageBodyPart.setDataHandler(new DataHandler(source));
                    messageBodyPart.setFileName(fileName);
                    multipart.addBodyPart(messageBodyPart);
                    String final_Text = "Name: " + name + "    " + "Email: " + email + "    " + "Post: " + post + "Phone: " + phone ;
                    textPart.setText(final_Text);
                    htmlPart.setContent("<a href='mailto:" + email + "'>Reply</a><br>", "text/html");
                    multipart.addBodyPart(htmlPart);
                    message.setSubject("Carrier Details: " + date);
                    multipart.addBodyPart(textPart);
                    message.setContent(multipart);
                    Transport.send(message);
                    HttpSession ss = request.getSession();
                    ss.setAttribute("msg", "Thank you " + name + ", your message has been submitted to us.");
                    response.sendRedirect("contact.jsp");
                } catch (Exception e) {
                    out.println(e);
                }

            } else {
                out.println("no file choosen....");
            }
        } catch (Exception e) {
            out.println(e);
        }
    }

    private String extractFileName(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String s : items) {
            if (s.trim().startsWith("filename")) {
                return s.substring(s.indexOf("=") + 2, s.length() - 1);
            }
        }
        return "";
    }

}