<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Student Registration</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${:\Users\damin\Downloads\tutor_buddy\tutor_buddy\users.txt}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<div>
    <div>
        <h2>Student Registration</h2>
        <%--@elvariable id="user" type="ch"--%>// it243
        <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/api/users/students/register" enctype="multipart/form-data">
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
                <form:input path="password" type="password" placeholder="Password" required="true"/>
            </div>
            <div>
                <label for="educationLevel">Select your education level</label>
                <form:select path="educationLevel">
                    <form:option value="School"/>
                    <form:option value="College/University"/>
                    <form:option value="Professional Development"/>
                </form:select>
            </div>
            <div>
                <form:input path="bio" placeholder="Short Bio"/>
            </div>
            <div>
                <label for="profilePicture">Profile Picture</label>
                <input type="file" name="profilePicture" accept="image/jpeg,image/png"/>
            </div>
            <div>
                <input type="submit" value="Register as Student"/>
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