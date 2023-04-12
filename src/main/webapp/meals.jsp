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
	<title>Meals</title>
	<link rel="stylesheet" href="css/style.css">
</head>
<body>

<h3><a href="index.html">Home</a></h3>
<h2>Meals</h2>
    <form method="get" action="meals">
        <input type="hidden" name="action" value="filter">
        <dl>
            <dt>From Date (inclusive):</dt>
            <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
        </dl>
        <dl>
            <dt>To Date (inclusive):</dt>
            <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
        </dl>
        <dl>
            <dt>From Time (inclusive):</dt>
            <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
        </dl>
        <dl>
            <dt>To Time (exclusive):</dt>
            <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
        </dl>
        <button type="submit">Filter</button>
    </form>
<a href="meals?action=create">Add meal</a>
<br>
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
			<jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
			<tr data-meal-excess="${meal.excess}">
				<td>${meal.date} ${meal.time}</td>
				<td>${meal.description}</td>
				<td>${meal.calories}</td>
				<td><a href="meals?action=update&id=${meal.id}">Edit</a></td>
				<td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
