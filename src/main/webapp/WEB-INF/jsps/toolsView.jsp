<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%  response.setCharacterEncoding("UTF-8"); request.setCharacterEncoding("UTF-8"); %>
<html>
<head>
	<meta charset="UTF-8">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link rel="stylesheet" href="css/styles.css"/>
	<title>Tools Menu</title>
</head>
<body>
<div class="container">
<h2>Tools Menu (${student})</h2>
<form action="showAllWords" method="post"><pre>
<button type="submit">Word List</button>
</pre></form>
<form action="showReport" method="post"><pre>
<button type="submit">Report</button>
</pre></form>
<form action="getFileName" method="post"><pre>
<button type="submit">Import</button>
</pre></form>
<form action="exportData" method="post"><pre>
<button type="submit">Export</button>
</pre></form>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>