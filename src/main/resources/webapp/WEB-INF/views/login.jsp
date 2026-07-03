<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Login</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<div>
    <div>
        <h2>TuitionConnect</h2>
        <%--@elvariable id="user" type=""--%>
        <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/api/auth/login">
            <div>
                <form:input path="email" type="email" placeholder="Email" required="true"/>
            </div>
            <div>
                <form:input path="password" type="password" placeholder="Password" required="true"/>
            </div>
            <div>
                <input type="submit" value="Login"/>
            </div>
            <div>
                <a href="#">Continue with Google</a>
                <a href="#">Continue with Apple</a>
            </div>
            <p>By signing in or creating an account, you agree with our
                <a href="#">Terms & Conditions</a> and
                <a href="#">Privacy Statement</a>.
            </p>
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/api/users/register">Register here</a></p>
        </form:form>
    </div>

    <div>
        <h3>Contact Us</h3>
        <p>Email: tuitionconnect@example.com</p>
        <p>Phone: +94 773790835</p>
        <p>Location: SLIIT Kurunegala</p>
    </div>
</div>

<footer>
    <p>© 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>