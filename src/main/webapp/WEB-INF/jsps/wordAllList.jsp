<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>List of All Words</title>
</head>
<body>
<div class="container">
<h2>List of all words (${student})</h2>
<table class="table table-bordered table-striped">
<thead>
<tr>
	<th>id</th>
	<th>word</th>
	<th>tracker</th>
	<th>sentence</th>
	<th>translation</th>
	<th>edit</th>
	<th>delete</th>
</tr>
</thead>
<tbody>
<c:forEach items="${words}" var="word">
<tr>
	<td>${word.id}</td>
	<td>${word.word}</td>
	<td>${word.tracker}</td>
	<td>${word.sentence}</td>
	<td>${word.translation}</td>
	<td><a href="editWord?word.id=${word.id}">edit</a></td>
	<td><a href="removeWord?word.id=${word.id}">remove</a></td>
</tr>
</c:forEach>
</tbody>
</table>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>