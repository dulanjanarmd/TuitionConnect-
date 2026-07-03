<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Registered Students</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>Registered Students</h2>
<table border="1">
    <tr>
        <th>Name</th>
        <th>Email</th>
        <th>Phone</th>
        <th>Subjects</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="user" items="${users}">
        <c:if test="${user.role == 'student'}">
            <tr>
                <td>${user.fullName}</td>
                <td>${user.email}</td>
                <td>${user.phone}</td>
                <td>${user.subjectsTaught}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/api/users/${user.id}">View</a>
                    <a href="${pageContext.request.contextPath}/api/users/${user.id}/delete">Delete</a>
                </td>
            </tr>
        </c:if>
    </c:forEach>
</table>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>