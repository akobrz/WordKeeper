<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>New Phrase</title>
</head>
<body>
<div class="container">
<form action="newPhraseAdded" method="post"><pre>
<h2>Add new Phrase (${student})</h2>
<label style="width: 600px; background-color: pink;">${section}</label>
<label>Sentence:</label><input type="text" name="sentence"/>
<label>Phrase:</label><input type="text" name="translation"/>
<label>Tracker:</label><input type="text" name="tracker"/>
<label style="width: 200px; padding:12px; text-align: center; background-color: lightgrey; color: red;">${msg}</label>
<button type="submit">Add new phrase</button>
</pre></form>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>