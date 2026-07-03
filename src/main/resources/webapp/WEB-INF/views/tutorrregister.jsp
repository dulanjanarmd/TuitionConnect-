<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Tutor Registration</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<div>
    <div>
        <h2>Tutor Registration</h2>
        <%--@elvariable id="tutor" type="com "--%>
        <form:form modelAttribute="tutor" method="post" action="${pageContext.request.contextPath}/api/tutors/register" enctype="multipart/form-data">
            <div>
                <form:input path="fullName" placeholder="Full Name" required="true"/>
            </div>
            <div>
                <form:input path="email" type="email" placeholder="Email" required="true"/>
            </div>
            <div>
                <form:input path="phone" placeholder="Phone" required="true"/>
            </div>
            <div>
                <form:input path="hourlyRate" type="number" step="0.01" placeholder="Hourly Rate ($)" required="true"/>
            </div>
            <div>
                <form:input path="education" placeholder="Education" required="true"/>
            </div>
            <div>
                <form:input path="experienceYears" type="number" placeholder="Experience (years)" required="true"/>
            </div>
            <div>
                <form:input path="subjectExpertise" type="number" step="0.1" placeholder="Subject Expertise (0-100)" required="true"/>
            </div>
            <div>
                <input type="submit" value="Register as Tutor"/>
                <p>Already registered? <a href="${pageContext.request.contextPath}/api/auth/login">Login here</a></p>
            </div>
        </form:form>
    </div>
</div>

<footer>
    <p>© 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>