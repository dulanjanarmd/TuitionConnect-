<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Manage Tutors</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>Manage Tutors</h2>
<div>
    <label for="subjectFilter">All Subjects</label>
    <select id="subjectFilter" onchange="window.location.href='${pageContext.request.contextPath}/tutormanagement?subject='+this.value">
        <option value="">All Subjects</option>
        <option value="OOP">OOP</option>
        <option value="DSA">DSA</option>
        <option value="DM">DM</option>
        <option value="TW">TW</option>
    </select>
    <label for="statusFilter">All Statuses</label>
    <select id="statusFilter" onchange="window.location.href='${pageContext.request.contextPath}/tutormanagement?status='+this.value">
        <option value="">All Statuses</option>
        <option value="active">Active</option>
        <option value="inactive">Inactive</option>
    </select>
</div>
<a href="#addTutor">Add New Tutor</a>

<table border="1">
    <tr>
        <th>Photo</th>
        <th>Name</th>
        <th>Subjects</th>
        <th>Rate</th>
        <th>Rating</th>
        <th>Status</th>
        <th>Actions</th>
    </tr>
    <c:forEach var="tutor" items="${tutors}" varStatus="loop">
        <tr>
            <td>${loop.count}</td>
            <td>${tutor.profilePicture}</td> <!-- Placeholder for image -->
            <td>${tutor.fullName}</td>
            <td>${tutor.subjects}</td>
            <td>${tutor.hourlyRate}</td>
            <td>${tutor.rating}</td>
            <td>${tutor.status}</td>
            <td>
                <a href="${pageContext.request.contextPath}/api/tutors/${tutor.id}">Edit</a>
                <a href="${pageContext.request.contextPath}/api/tutors/${tutor.id}/delete">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>

<div id="addTutor">
    <h3>Add New Tutor</h3>
    <%--@elvariable id="tutor" type="ch"--%>
    <%--@elvariable id="tutor" type="com"--%>
    <form:form modelAttribute="tutor" method="post" action="${pageContext.request.contextPath}/api/tutors/register" enctype="multipart/form-data">
        <div>
            <label>Full Name</label>
            <form:input path="fullName"/>
        </div>
        <div>
            <label>Email</label>
            <form:input path="email"/>
        </div>
        <div>
            <label>Phone</label>
            <form:input path="phone"/>
        </div>
        <div>
            <label>Hourly Rate ($)</label>
            <form:input path="hourlyRate" type="number" step="0.01"/>
        </div>
        <div>
            <label>Bio</label>
            <form:textarea path="bio" rows="5" cols="30"/>
        </div>
        <div>
            <label>Education</label>
            <form:input path="education"/>
        </div>
        <div>
            <label>Experience (years)</label>
            <form:input path="experienceYears" type="number"/>
        </div>
        <div>
            <label>Subjects</label>
            <form:select path="subjects" multiple="true">
                <form:option value="OOP"/>
                <form:option value="DSA"/>
                <form:option value="DM"/>
                <form:option value="TW"/>
            </form:select>
        </div>
        <div>
            <label>Availability</label>
            <div>
                <select name="availability[0].day">
                    <option value="Monday">Monday</option>
                    <!-- Add other days -->
                </select>
                <input name="availability[0].startTime" type="time"/>
                <span>to</span>
                <input name="availability[0].endTime" type="time"/>
                <button type="button">Add Time Slot</button>
            </div>
        </div>
        <div>
            <label>Status</label>
            <form:select path="status">
                <form:option value="active"/>
                <form:option value="inactive"/>
            </form:select>
        </div>
        <div>
            <label>Profile Picture</label>
            <input type="file" name="profilePicture" accept="image/jpeg,image/png"/>
            <p>JPG, PNG, max 2MB</p>
        </div>
        <input type="submit" value="Save Tutor"/>
        <a href="${pageContext.request.contextPath}/tutormanagement">Cancel</a>
    </form:form>
</div>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>