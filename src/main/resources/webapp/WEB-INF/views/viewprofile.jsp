<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - My Profile</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>My Profile</h2>
<c:if test="${not empty user}">
    <div>
        <h3>${user.fullName}</h3>
        <p>${user.email}</p>
        <div>
            <p>Phone</p>
            <p>${user.phone}</p>
        </div>
        <div>
            <p>Role</p>
            <p>${user.role}</p>
        </div>
        <div>
            <p>Joined</p>
            <p>${user.joinedDate}</p>
        </div>
        <div>
            <p>Bio</p>
            <p>${user.bio}</p>
        </div>
        <div>
            <p>Subjects Taught</p>
            <p>${user.subjectsTaught}</p>
        </div>
        <div>
            <a href="#editProfile">Edit Profile</a>
            <a href="#deleteAccount">Delete Account</a>
        </div>
    </div>
</c:if>

<div id="editProfile">
    <h3>Edit Profile</h3>
    <%--@elvariable id="user" type="com"--%>
    <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/api/users/${user.id}" enctype="multipart/form-data">
        <div>
            <label>Full Name</label>
            <form:input path="fullName" required="true"/>
            <form:errors path="fullName" cssClass="error"/>
        </div>
        <div>
            <label>Email</label>
            <form:input path="email" type="email" required="true"/>
            <form:errors path="email" cssClass="error"/>
        </div>
        <div>
            <label>Phone</label>
            <form:input path="phone"/>
        </div>
        <div>
            <label>Role</label>
            <form:select path="role">
                <form:option value="student">Student</form:option>
                <form:option value="tutor">Tutor</form:option>
            </form:select>
        </div>
        <div>
            <label>Bio</label>
            <form:textarea path="bio" rows="5" cols="30"/>
        </div>
        <div>
            <label>Subjects Taught (comma-separated)</label>
            <form:input path="subjectsTaught"/>
        </div>
        <div>
            <label>Profile Picture</label>
            <input type="file" name="profilePicture" accept="image/jpeg,image/png" />
            <p>JPG, PNG, max 2MB</p>
            <c:if test="${fileSizeError != null}">
                <p style="color:red">${fileSizeError}</p>
            </c:if>
        </div>
        <input type="submit" value="Save Changes"/>
        <a href="${pageContext.request.contextPath}/api/users/${user.id}">Cancel</a>
    </form:form>
</div>

<div id="deleteAccount">
    <h3>Delete Account</h3>
    <p>Warning: Deleting your account is irreversible. All your data, including bookings, profile information, and account details, will be permanently removed.</p>
    <form:form modelAttribute="user" method="post" action="${pageContext.request.contextPath}/api/users/${user.id}/delete">
        <div>
            <label>Password</label>
            <form:input path="password" type="password" required="true"/>
            <form:errors path="password" cssClass="error"/>
        </div>
        <div>
            <label>Type "DELETE" to confirm</label>
            <form:input path="confirmDelete" required="true"/>
            <form:errors path="confirmDelete" cssClass="error"/>
        </div>
        <input type="submit" value="Delete Account"/>
        <a href="${pageContext.request.contextPath}/api/users/${user.id}">Cancel</a>
    </form:form>
</div>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>