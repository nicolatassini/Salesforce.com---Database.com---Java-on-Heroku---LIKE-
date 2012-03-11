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
	Search: <input type="text" name="q" <c:if test="${q != null}">value="${q}"</c:if> /><br/>
	Page Size: 
	<select name="n">
		<option value="10" <c:if test="${n != null && n == 10}">selected="selected" </c:if> >10</option>
		<option value="20" <c:if test="${n != null && n == 20}">selected="selected" </c:if> >20</option>
		<option value="50" <c:if test="${n != null && n == 50}">selected="selected" </c:if> >50</option>
		<option value="100" <c:if test="${n != null && n == 100}">selected="selected" </c:if> >100</option>
		<option value="200" <c:if test="${n != null && n == 200}">selected="selected" </c:if> >200</option>
		<option value="500" <c:if test="${n != null && n == 500}">selected="selected" </c:if> >500</option>
		<option value="1000" <c:if test="${n != null && n == 1000}">selected="selected" </c:if> >1000</option>
	</select>
	<br/>
	<input type="hidden" name="o" value="0" />
	<input type="submit" name="Submit" value="Submit" />
</form> 

<c:if test="${results != null}">
	Total results: <b>${totalResults}</b>
	
	<c:if test="${hasPrevious}">
		<a target="_blank" href="">Previous <</a>
	</c:if>
	<c:if test="${hasNext}">
		<a target="_blank" href="">> Next</a>
	</c:if>

	
	<br/>
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
