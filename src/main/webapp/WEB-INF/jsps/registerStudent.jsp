<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
		<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Register Student</title>
</head>
<body>
<div class="container">
	<h2>User Registration:</h2>
	<form action="studentRegistered" method="post"><pre>
	<label>First Name:</label><input type="text" name="firstName"/>
	<label>Last Name:</label><input type="text" name="lastName"/>
	<label>User email:</label><input type="text" name="email"/>
	<label>Password:</label><input type="password" name="password" />
	<label>Confirm password:</label><input type="password" name="confirmPassword" />
	<button type="submit">Register</button>
	</pre></form>
	<form action="quit" method="post"><pre>
	<button type="submit">Return</button>
	</pre></form>
</div></body>
</html>