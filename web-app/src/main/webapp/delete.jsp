<%@ page import="java.sql.Connection" %>
<%@ page import="java.sql.DriverManager" %>
<%@ page import="java.sql.SQLException" %>
<%@ page import="java.sql.PreparedStatement" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>NoteStack</title>
    <link rel="stylesheet" type="text/css" href="styles.css">
    <script>
        const subjects = {
            sem1: ["Mathematics-I", "Essence of Indian Traditional knowledge", "Environmental Science", "Chemistry", "Programming for Problem Solving"],
            sem2: ["Indian Constitution", "English", "Physics", "Mathematics-II", "Basic Electrical Engineering"],
            sem3: ["Operations Research", "Basic Electronics", "Digital Electronics", "Data Structures and Algorithms", "Discrete Mathematics", "Programming Languages"],
            sem4: ["ETC", "Finance and Accounting", "Mathematics – III (Probability & Statistics)", "Signals and Systems", "OOP using JAVA", "Computer Organization", "Database Management Systems"],
            sem5: ["Software Engineering", "Operating Systems", "Automata Languages & Computation", "Computer Networks", "Artificial Intelligence", "Data Science"],
            sem6: ["Image Processing", "Compiler Design", "Cyber Law & Ethics", "Design and Analysis of Algorithms", "Machine Learning", "Cryptography and Network Security"]
        };

        function updateSubjects() {
            const sem = document.getElementById("semester").value;
            const subjectSelect = document.getElementById("subject");
            subjectSelect.innerHTML = "";
            subjects[sem].forEach(subject => {
                const option = document.createElement("option");
                option.value = subject;
                option.text = subject;
                subjectSelect.add(option);
            });
        }
        
        window.onload = function() {
            document.getElementById("semester").value = "sem1";
            updateSubjects();
        };
    </script>
</head>

<body>
    <div id="background-wrap"></div>
    <div class="wrapper">
        <section class="navigation">
            <div class="nav-container">
                <div class="brand">
                    <a href="home.html">NoteStack</a>
                </div>
                <nav>
                    <div class="nav-mobile">
                        <a id="nav-toggle" href="#!"><span></span></a>
                    </div>
                    <ul class="nav-list">
                        <li><a href="index.html">Home</a></li>
                        <li><a href="about.html">About</a></li>
                        <li><a href="contact.html">Contact Us</a></li>
                    </ul>
                </nav>
            </div>
        </section>
        <br><br><br>
        <center>
            <form method="get" action="delete.jsp">
                <label for="semester">Select Semester:</label>
                <select id="semester" name="semester" onchange="updateSubjects()">
                    <option value="sem1">Sem 1</option>
                    <option value="sem2">Sem 2</option>
                    <option value="sem3">Sem 3</option>
                    <option value="sem4">Sem 4</option>
                    <option value="sem5">Sem 5</option>
                    <option value="sem6">Sem 6</option>
                </select>
                <br><br>
                <label for="subject">Select Subject:</label>
                <select id="subject" name="subject"></select>
                <br><br>
                <input type="submit" value="Filter">
            </form>
            <br><br>
            <% 
            String selectedSemester = request.getParameter("semester");
            String selectedSubject = request.getParameter("subject");
            if (selectedSubject != null && !selectedSubject.isEmpty()) {
                Connection con = null;
                PreparedStatement ps = null;
                ResultSet rs = null;
                try {
                    String dbURL = getServletContext().getInitParameter("dbURL");
                    String dbUser = getServletContext().getInitParameter("dbUser");
                    String dbPassword = getServletContext().getInitParameter("dbPassword");

                    Class.forName("com.mysql.cj.jdbc.Driver");
                    con = DriverManager.getConnection(dbURL, dbUser, dbPassword);
                    String sql = "SELECT * FROM employee WHERE semester = ? AND subject = ?";
                    ps = con.prepareStatement(sql);
                    ps.setString(1, selectedSemester);
                    ps.setString(2, selectedSubject);
                    rs = ps.executeQuery();
        %>
            <table border="1">
                <tr>
                    <th>ID</th>
                    <th>SEM</th>
                    <th>Subject</th>
                    <th>File Name</th>
                    <th>File Path</th>
                    <th>Added Date</th>
                    <th>Delete</th>
                </tr>
                <% while (rs.next()) { %>
                    <tr>
                        <td><%= rs.getInt("id") %></td>
                        <td><%= rs.getString("semester") %></td>
                        <td><%= rs.getString("subject") %></td>
                        <td><%= rs.getString("filename") %></td>
                        <td><%= rs.getString("path") %></td>
                        <td><%= rs.getTimestamp("added_date") %></td>
                        <td><a href="DeleteFileServlet?fileName=<%= rs.getString("filename") %>">Delete</a></td>
                    </tr>
                    <% } %>
            </table>
            <% 
                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    } finally {
                        if (rs != null) try { rs.close(); } catch (SQLException ignore) {}
                        if (ps != null) try { ps.close(); } catch (SQLException ignore) {}
                        if (con != null) try { con.close(); } catch (SQLException ignore) {}
                    }
                }
        %>
            <br>
        </center>
    </div>
    <footer>
        <div class="foot">
            <div class="left">
                <p>&copy; 2022 NoteStack. All rights reserved.</p>
                <p>Contact us: <a href="mailto:support@notestack.com">support@notestack.com</a></p>
            </div>
        </div>
    </footer>
</body>
</html>
