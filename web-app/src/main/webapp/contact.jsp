<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Email With Attachments</title>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="styles.css">
    <style>
        body {
            position: relative;
            padding-bottom: 50px; /* Adjust as necessary */
        }
        .container {
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 80vh; /* Adjusted to leave space for the footer */
            flex-direction: column;
        }
        .contact-form {
            width: 100%;
            max-width: 600px;
        }
        .home-link {
            display: block;
            text-align: center;
            margin: 20px 0;
            color: #337ab7; /* Bootstrap default link color */
        }
        footer {
            text-align: center;
            padding: 10px;
            background-color: #1f1209;
            position: fixed;
            bottom: 0;
            width: 100%;
        }
    </style>
</head>
<body>
    <div id="background-wrap"></div>
    <section class="navigation">
        <div class="nav-container">
            <div class="brand">
                <img src="logo.jpeg" alt="Logo" class="logo" />
                <a href="home.html">notesstack</a>
            </div>
            <nav>
                <div class="nav-mobile">
                    <a id="nav-toggle" href="#!"><span></span></a>
                </div>
                <ul class="nav-list">
                    <li><a href="file-list.jsp">Home</a></li>
                    <li><a href="about.html">About</a></li>
                    <li><a href="contact.jsp">Contact Us</a></li>
                </ul>
            </nav>
        </div>
    </section>

    <div class="container">
        <div class="row">
            <center><h2>CONTACT US.</h2></center>
            <div class="contact-form text-center">
            <%
                            String msg = (String) session.getAttribute("msg");
                            if (msg != null) {
                                out.println("<center><div style='color:green;'>" + msg + "</div></center>");
                                session.removeAttribute("msg");
                            } else {
                            }
                        %>
                <form id="form" enctype="multipart/form-data" action="ContactServlet" method="post" accept-charset="utf-8">
                            <div class="form-group">
                                <input type="text" required="" placeholder="Roll Number" class="form-control" name="name" id="name">
                            </div>
                            <div class="form-group">
                                <input type="text" required="" placeholder="semester" class="form-control" name="phone" id="phone">
                            </div>

                            <div class="form-group">
                                <input type="text" required="" placeholder="subject" class="form-control" name="post" id="post">
                            </div>

                            <div class="form-group">
                                <input type="email" required="" placeholder="Your Email" class="form-control" name="email" id="email">
                            </div>
                           
                            <div class="form-group">
                                <input type="file"  placeholder="Subject" class="form-control" name="resume" id="resume">
                            </div>

                            <div id="cf-submit">
                                <input type="submit" id="submit" class="btn btn-info" value="Submit">
                            </div>

                        </form>
            </div>
        </div>
        <a href="login.jsp" class="home-link">LOGIN</a>
    </div>

    <footer>
        <div class="foot">
            <div class="left">
                <p>&copy; 2022 notesstack. All rights reserved.</p>
                <p>Contact us: <a href="mailto:support@notesstack.com">support@notesstack.com</a></p>
            </div>
        </div>
    </footer>
</body>
</html>
