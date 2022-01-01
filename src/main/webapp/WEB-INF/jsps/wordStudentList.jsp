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
	<title>List of Words</title>
</head>
<body>
<div class="container">
<h2>List of my words (${student})</h2>
<table class="table table-bordered table-striped">
<thead>
<tr>
	<th>id</th>
	<th>word</th>
	<th>tracker</th>
	<th>sentence</th>
	<th>translation</th>
	<th>last repetition</th>
	<th>next repetition</th>
</tr>
</thead>
<c:forEach items="${words}" var="word">
<tr>
	<td>${word.id}</td>
	<td>${word.word}</td>
	<td>${word.tracker}</td>
	<td>${word.sentence}</td>
	<td>${word.translation}</td>
	<td>${word.last_repetition}</td>
	<td>${word.next_repetition}</td>
</tr>
</c:forEach>
</table>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>