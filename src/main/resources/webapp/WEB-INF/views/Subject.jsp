<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Subjects & Tutors</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>Subjects & Tutors</h2>
<div>
    <h3>Subjects</h3>
    <c:forEach var="subject" items="${subjects}">
        <div>
            <h4>${subject.name}</h4>
            <!-- Add tutor list for each subject if needed -->
        </div>
    </c:forEach>
</div>
<div>
    <h3>Tutors</h3>
    <c:forEach var="tutor" items="${tutors}">
        <div>
            <p>${tutor.fullName} - Subjects: ${tutor.subjects}</p>
        </div>
    </c:forEach>
</div>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>