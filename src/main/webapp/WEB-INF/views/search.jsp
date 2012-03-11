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
		<option value="20">20</option>
		<option value="50">50</option>
		<option value="100">100</option>
		<option value="200">200</option>
		<option value="500">500</option>
		<option value="1000">1000</option>
	</select>
	<br/>
	<input type="hidden" name="o" value="0" />
	<input type="submit" name="Submit" value="Submit" />
</form> 

<c:if test="${results != null}">
	Total results: <b>${totalResults}</b><br/>
	
	<table>
		<tr>
			<th>Details</th>
			<th>Tweet</th>
		</tr>
		<c:forEach var="result" items="${results}">
        <tr>
          <td><a target="_blank" href="https://na14.salesforce.com/${result.sfid}">Details</a></td>
          <td>${result.tweet}</td>
        </tr>
      </c:forEach>
	</table>
	
</c:if>

<br/>
<br/>
<a href="/">Home</a> | <a href="/search/index">Index</a> | <a href="/search/query">Query</a>
</body>
</html>
