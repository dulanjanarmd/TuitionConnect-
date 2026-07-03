<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - My Bookings</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>My Bookings</h2>
<p>View and manage your upcoming and past tutoring sessions.</p>
<div>
    <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}">All Bookings</a>
    <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}&status=upcoming">Upcoming</a>
    <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}&status=completed">Completed</a>
    <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}&status=canceled">Cancelled</a>
</div>

<c:if test="${not empty bookings}">
    <table border="1">
        <tr>
            <th>Tutor</th>
            <th>Subject</th>
            <th>Date</th>
            <th>Time</th>
            <th>Status</th>
        </tr>
        <c:forEach var="booking" items="${bookings}">
            <tr>
                <td>${booking.tutorId}</td> <!-- Replace with tutor name if available -->
                <td>${booking.subject}</td>
                <td>${booking.sessionDate}</td>
                <td>${booking.startTime} - ${booking.endTime}</td>
                <td>${booking.status}</td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<c:if test="${empty bookings}">
    <div>
        <h3>No Bookings Found</h3>
        <p>You don't have any bookings in this category. <a href="${pageContext.request.contextPath}/api/bookings/search">Book a tutor now</a> to get started.</p>
    </div>
</c:if>

<footer>
    <p>&copy; 2025 TuitionConnect. All rights reserved.</p>
</footer>
</body>
</html>