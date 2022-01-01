<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Default Section</title>
</head>
<body>
<div class="container">
<h2>Select default section (${student})</h2>
<label style="width: 600px; background-color: pink;">Default Section: ${dsection}</label>
<table class="table table-bordered table-striped">
<thead>
<tr>
	<th>id</th>
	<th>value</th>
	<th>default?</th>
</tr>
</thead>
<c:forEach items="${sections}" var="section">
<tr>
	<td>${section.id}</td>
	<td>${section.value}</td>
	<td><a href="setDefaultSection?section.id=${section.id}">set default</a></td>
</tr>
</c:forEach>
</table>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>