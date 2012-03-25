<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	<script type="text/javascript" src="/resources/js/jquery-1.7.1.min.js" ></script>

	<script src="/resources/js/jquery-ui.min.js" type="text/javascript"></script>
    <script src="/resources/js/jquery.indextank.ize.js" type="text/javascript"></script>
    <script src="/resources/js/jquery.indextank.autocomplete.js" type="text/javascript"></script> 
    <link href="/resources/css/jquery-ui.css" rel="stylesheet" />
    
    <script src="/resources/js/jquery.dataTables.min.js" type="text/javascript"></script>
    <link href="/resources/css/jquery.dataTables.css" rel="stylesheet" />
    
    <link href="/resources/css/home.css" rel="stylesheet" />
    
	<script type="text/javascript">
		var publicApiUrl = "http://dhyja.api.searchify.com";
        var indexName = "sfdc_index";
        
        $(function() {
            $("#searchForm").indextank_Ize(publicApiUrl, indexName);
            $("#searchInput").indextank_Autocomplete();
            $('#resultTable').dataTable();
        });
        
		function changePage(_num) {
			_prevVal = parseInt($('#offsetField').val());
			$('#offsetField').val(_prevVal + parseInt(_num));
			$('#submitButton').click();
		}
	</script>
</head>
<body>
	<h1 id="headerTitle">
		salesforce.com database.com Heroku Java Search Engine
	</h1>
	<div id="headerContainer">
		<a href="/">
			<img src="/resources/img/header_img.png" />
		</a>
	</div>

	<div id="inputContainer">
		<form submit="/search/query" method="POST" id="searchForm" >
			<input id="searchInput" type="text" name="q" <c:if test="${q != null}">value="${q}"</c:if> /> 
			<select name="n">
				<option value="10" <c:if test="${n != null && n == 10}">selected="selected" </c:if> >10</option>
				<option value="20" <c:if test="${n != null && n == 20}">selected="selected" </c:if> >20</option>
				<option value="50" <c:if test="${n != null && n == 50}">selected="selected" </c:if> >50</option>
				<option value="100" <c:if test="${n != null && n == 100}">selected="selected" </c:if> >100</option>
				<option value="200" <c:if test="${n != null && n == 200}">selected="selected" </c:if> >200</option>
				<option value="500" <c:if test="${n != null && n == 500}">selected="selected" </c:if> >500</option>
				<option value="1000" <c:if test="${n != null && n == 1000}">selected="selected" </c:if> >1000</option>
			</select>
			<input type="hidden" name="o" id="offsetField" <c:if test="${o != null}">value="${o}"</c:if> />
			<input type="submit" id="submitButton" name="Submit" value="SEARCH" />
		</form>
	</div> 

<c:if test="${results != null}">
	Total results: <b>${totalResults}</b>
	
	<c:if test="${hasPrevious}">
		<a href="#" onclick="javascript:return changePage(-1);" >Previous <</a>
	</c:if>
	<c:if test="${hasNext}">
		<a href="#" onclick="javascript:return changePage(1);" >> Next</a>
	</c:if>
	
	<br/>
	<table id="resultTable">
		<thead>
			<tr>
				<th>Details</th>
				<th>Tweet</th>
			</tr>
		</thead>
		<c:forEach var="result" items="${results}">
		<tbody>
	        <tr>
	        	<td>${result.row}</td>
	        	<td><a target="_blank" href="https://na14.salesforce.com/${result.sfid}">Details</a></td>
	        	<td>${result.tweet}</td>
	        </tr>
	    </tbody>
      </c:forEach>
	</table>
</c:if>

<br/>
<a href="/search/index">Index</a>
</body>
</html>
