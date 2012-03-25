<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
	<title>Home</title>
	
	<link href="/resources/css/home.css" rel="stylesheet" />
</head>
<body class="textbasic" >
	<h1 id="headerTitle">
		salesforce.com database.com Heroku Java Search Engine
	</h1>
	<div id="headerContainer">
		<a href="/">
			<img src="/resources/img/header_img.png" />
		</a>
	</div>
	${controllerMessage}
	<br/>
	<br/>
</body>
</html>
