<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Learn</title>
</head>
<body>
<div class="container">
<h2>Learn (${student})</h2>
<pre>
<label style="width: 400px; background-color: pink;">You have ${number_words_to_learn} new words to learn</label>
<label style="width: 400px; background-color: pink">You have ${number_words_to_repetition} words to repetition</label>
</pre>
<form action="learnNewWords" method="post"><pre>
<button type="submit">Learn</button>
</pre></form>
<form action="wordsRepetition" method="post"><pre>
<button type="submit">Repetition</button>
</pre></form>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>