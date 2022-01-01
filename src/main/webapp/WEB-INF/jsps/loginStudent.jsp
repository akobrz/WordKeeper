<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Login or Register</title>
</head>
<body>
<div class="container">
<h2>Login or Register</h2> <pre>
<form action="loginStudent" method="post">
<label>Email:</label><input type="text" name="email" placeholder="email"/>
<label>Password:</label><input type="password" name="password" placeholder="password"/>
<label style="background-color: lightgrey;">${msg}</label>
<button type="submit">Login</button>
</pre></form>
<form action="showReg" method="post"><pre>
<button type="submit">Registration</button>
</pre></form>
</div>
</body>
</html>