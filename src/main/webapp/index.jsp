<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login Room Reservation System</title>
</head>

<body>
    <h1><br>*.*.* In the name of God *.*.*<br></h1>
    <pre>* Welcome to the hotel room reservation system *</pre>

    <hr>

    <h3># Please Login: </h3>

    <div>
        <form method="post" action="loginServlet">
            <h3>User name</h3>
            <input type="text" name="userName">
            <h3>Password</h3>
            <input type="password" name="password">
            <br>
            <br>
            <input type="submit" value="Login">
        </form>
    </div>
</body>

<style>
    body {
        background-color: beige;
        font-family: "Lucida Console", "Courier New", monospace;
        height: auto;
        width: 900px;
        margin: auto auto 20px auto;
    }

    pre {
        font-family: "Lucida Console", "Courier New", monospace;
        font-size: 20px;
        text-align: center
    }

    h1 {
        text-align: center;
        color: crimson;
        font-size: 25px;
    }

    hr {
        width:100%;
        text-align:center;
        margin-left:0;
        color: crimson;
    }

    input {
        font-size: large;
        padding: 5px;
    }

    div {
        width: auto;
        text-align: center;
        background-color: gainsboro;
        margin-top: 20px;
        padding: 10px 0 20px 20px;
    }

    submit {
        font-family: "Lucida Console", "Courier New", monospace;
        font-size: large;
    }
</style>
</html>
