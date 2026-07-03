<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/bookings/search">Book a Tutor</a>
    <a href="#">Contact</a>
    <c:if test="${not empty user}">
        <a href="${pageContext.request.contextPath}/api/users/${user.id}">View Profile</a>
        <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}">View Booking</a>
    </c:if>
    <a href="${pageContext.request.contextPath}/api/subjects">Subject and Tutors</a>
</nav>

<c:if test="${empty user}">
    <div>
        <h3>Login</h3>
        <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
        <h3>Sign Up</h3>
        <a href="${pageContext.request.contextPath}/api/users/register">As Student</a>
        <a href="${pageContext.request.contextPath}/api/tutors/register">As Tutor</a>
    </div>
</c:if>
<c:if test="${not empty user}">
    <div>
        <h3>Welcome, ${user.fullName}!</h3>
        <a href="${pageContext.request.contextPath}/api/auth/logout">Logout</a>
    </div>
</c:if>

<section>
    <h2>Personalized Academic Success</h2>
    <p>Connect with top-rated tutors in Mathematics, Science, English, and more.</p>
    <a href="${pageContext.request.contextPath}/api/bookings/search">Book a Tutor Now</a>
</section>

<section>
    <h2>Explore Subjects</h2>
    <c:forEach var="subject" items="${subjects}">
        <div>
            <h3>${subject.name}</h3>
            <p>Master ${subject.name.toLowerCase()} with expert tutors.</p>
            <a href="${pageContext.request.contextPath}/api/tutors?subject=${subject.name}">Learn More</a>
        </div>
    </c:forEach>
</section>

<section>
    <h2>Our Users</h2>
    <!-- Dynamic user list to be populated by controller -->
</section>

<section>
    <h2>What Our Students Say</h2>
    <c:forEach var="review" items="${reviews}">
        <div>
            <p>${review.reviewText}</p>
            <p>- ${review.tutorName}, ${review.userId}</p>
        </div>
    </c:forEach>
</section>

<footer>
    <div>
        <h3>TuitionConnect</h3>
        <p>Your personalized path to academic success through quality home tutoring.</p>
        <div>
            <a href="#">Facebook</a>
            <a href="#">Twitter</a>
            <a href="#">Instagram</a>
        </div>
    </div>
    <div>
        <h3>Quick Links</h3>
        <a href="${pageContext.request.contextPath}/api/bookings/search">Book a Tutor</a>
        <a href="${pageContext.request.contextPath}/api/subjects">Subjects</a>
        <a href="#">Contact Us</a>
        <a href="${pageContext.request.contextPath}/api/tutors/management">Tutor Management</a>
        <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
        <a href="${pageContext.request.contextPath}/api/users/register">Register as Student</a>
        <a href="${pageContext.request.contextPath}/api/tutors/register">Register as Tutor</a>
    </div>
    <div>
        <h3>Support</h3>
        <a href="#">FAQ</a>
        <a href="#">Privacy Policy</a>
        <a href="#">Terms of Service</a>
        <a href="#">Help Center</a>
    </div>
    <p>© 2025 TuitionConnect. All rights reserved. | Last updated: May 16, 2025, 12:45 PM +0530</p>
</footer>
</body>
</html>