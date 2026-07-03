<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Submit a Tutor Review</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>Submit a Tutor Review</h2>
<%--@elvariable id="review" type="ch"--%>// it24103951
<form:form modelAttribute="review" method="post" action="${pageContext.request.contextPath}/api/reviews">
    <div>
        <label for="tutorName">Select Tutor:</label>
        <form:select path="tutorName">
            <form:option value="" label="-- Choose a Tutor --"/>
            <form:option value="Mr. Pubudu Senanayake" label="Mr. Pubudu Senanayake"/>
            <form:option value="Ms. Amal Silva" label="Ms. Amal Silva"/>
            <form:option value="Dr. Nimal Perera" label="Dr. Nimal Perera"/>
        </form:select>
    </div>
    <div>
        <label for="reviewText">Your Review:</label>
        <form:textarea path="reviewText" rows="5" cols="30"/>
    </div>
    <div>
        <label>Rating:</label>
        <form:input path="rating" type="number" min="1" max="5" required="true"/>
        <span>★</span><span>★</span><span>★</span><span>★</span><span>★</span>
    </div>
    <form:hidden path="userId" value="${user.id}"/>
    <input type="submit" value="Submit Review"/>
</form:form>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>