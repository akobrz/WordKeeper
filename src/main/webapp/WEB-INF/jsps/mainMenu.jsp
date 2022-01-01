<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
        <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Main Menu</title>
</head>
<body>
<div class="container">
<h2>Main Menu (${student})</h2>
<form action="addNewWord" method="post"><pre>
<button type="submit">Add a new word</button>
</pre></form>
<form action="addNewPhrase" method="post"><pre>
<button type="submit">Add a new phrase</button>
</pre></form>
<form action="viewWords" method="post"><pre>
<button type="submit">View</button>
</pre></form>
<form action="learnWords" method="post"><pre>
<button type="submit">Learn</button>
</pre></form>
<form action="flashcardMenu" method="post"><pre>
<button type="submit">Flashcards</button>
</pre></form>
<form action="showSectionsMenu" method="post"><pre>
<button type="submit">Sections</button>
</pre></form>
<form action="toolsMenu" method="post"><pre>
<button type="submit">Tools</button>
</pre></form>
<form action="quit" method="post"><pre>
<button type="submit">Logout</button>
</pre></form>
</div>
</body>
</html>