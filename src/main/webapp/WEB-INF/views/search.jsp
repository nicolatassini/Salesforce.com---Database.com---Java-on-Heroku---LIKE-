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
	Page Size: 
	<select name="n">
		<option value="10">10</option>
		<option value="10">20</option>
		<option value="10">50</option>
		<option value="10">100</option>
		<option value="10">200</option>
	</select>
	<br/>
	<input type="hidden" name="o" value="0" />
	<input type="submit" name="Submit" value="Submit" />
</form> 

<c:if test="${results != null}">
	Total results: <b>${totalResults}</b><br/>

	<c:forEach var="result" items="${results}">
        <tr>
          <td>${result.sfid}</td>
          <td>${result.tweet}</td>
        </tr>
      </c:forEach>

</c:if>

<br/>
<br/>
<a href="/">Home</a> | <a href="/search/index">Index</a> | <a href="/search/query">Query</a>
</body>
</html>
