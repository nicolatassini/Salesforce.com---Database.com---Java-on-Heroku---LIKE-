<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
</head>
<body>
<h1>
	SFDC Heroku Java Search Engine
</h1>

<form submit="/search/query" method="POST" >
	Search: <input type="text" name="q" /><br/>
	Page Size: <select name="n">
		<option value="10">10</option>
		<option value="10">20</option>
		<option value="10">50</option>
		<option value="10">100</option>
		<option value="10">200</option>
		</select>
	<input type="hidden" name="o" value="0" />
	<input type="submit" name="Submit" value="Submit" />
</form> 

<br/>
<br/>
<a href="/">Home</a> | <a href="/search/index">Index</a> | <a href="/search/query">Query</a>
</body>
</html>