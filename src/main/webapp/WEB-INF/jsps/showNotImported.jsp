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
	<title>Import</title>
</head>
<body>
<div class="container">
<h2>Import (${student})</h2>
<pre>
<label style="width: 600px;">ERROR: Data not imported from file: ${file_name}</label>
</pre>
<form action="mainMenu" method="post"><pre>
<button type="submit">Return</button>
</pre></form>
</div>
</body>
</html>