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
	Page Size: <input type="text" name="n" />
	<input type="hidden" name="o" value="0" />
	<input type="submit" name="Submit" value="Submit" />
</form> 

<br/>
<br/>
<a href="/">Home</a> | <a href="/search/index">Index</a> | <a href="/search/query">Query</a>
</body>
</html>
