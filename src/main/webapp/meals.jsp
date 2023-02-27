<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Vredn
  Date: 24.02.2023
  Time: 22:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
	<title>Meal List</title>
	<style>
		.normal{
			color: green;
		}
		.exceed{
			color: red;
		}
	</style>
</head>
<body>

<h2>Meals</h2>
<table border="1" cellpadding="8" cellspacing="0">
	<c:set var="meals" value = "${requestScope.meals}"/>
	<thead>
			<tr>
				<th>Date</th>
				<th>Description</th>
				<th>Calories</th>
			</tr>
		</thead>
		<c:forEach items="${meals}" var="meal">
			<jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealTo"/>
			<tr class="${meal.excess ? 'exceed' : 'normal'}">
				<td>${meal.date} ${meal.time}</td>
				<td>${meal.description}</td>
				<td>${meal.calories}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
