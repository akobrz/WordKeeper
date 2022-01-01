<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>	
	<title>Edit Word</title>
</head>
<body>
<div class="container">
<h2>Edit Word (${student})</h2>
<form action="wordModified" method="post"><pre>
<label>Word:</label><input type="text" name="word" value="${entry.word}"/>
<label>Sentence:</label><input type="text" name="sentence" value="${entry.sentence}"/>
<label>Tracker:</label><input type="text" name="tracker" value="${entry.tracker}"/>
<label>Translation:</label><input type="text" name="translation" value="${entry.translation}"/>
<button type="submit">Modify</button>
</pre></form>
<form action="showAllWords" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>