<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servlet File Upload/Download</title>
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
        <br><br><br><br>
        <center>
            <form action="UploadServlet" method="post" enctype="multipart/form-data">
                <table width="400px" align="center" border=2>
                    <tr>
                        <td align="center" colspan="2">Form Details</td>
                    </tr>
                    <tr>
                        <td>SEM </td>
                        <td>
                            <select id="semester" name="semester" onchange="updateSubjects()">
                                <option value="sem1">Sem 1</option>
                                <option value="sem2">Sem 2</option>
                                <option value="sem3">Sem 3</option>
                                <option value="sem4">Sem 4</option>
                                <option value="sem5">Sem 5</option>
                                <option value="sem6">Sem 6</option>
                                <!-- Add more semesters if needed -->
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Subject </td>
                        <td>
                            <select id="subject" name="subject">
                                <!-- Options will be populated based on selected semester -->
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>Select File </td>
                        <td>
                            <input type="file" required="" name="file">
                        </td>
                    </tr>
                    <tr>
                        <td></td>
                        <td><input type="submit" value="Submit"></td>
                    </tr>
                </table>
            </form>
            <br><a href="file-list.jsp">View List</a>
            <%
                if (request.getAttribute("msg") != null) {
                    out.println("<p style='color:green;'>" + request.getAttribute("msg") + "</p>");
                }
            %>
        </center>
    </body>
</html>
