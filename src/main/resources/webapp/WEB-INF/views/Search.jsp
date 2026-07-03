<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <title>TuitionConnect - Book Your Tutor</title>
</head>
<body>
<h1>TuitionConnect</h1>
<nav>
    <a href="${pageContext.request.contextPath}/api/auth/login">Login</a>
    <a href="${pageContext.request.contextPath}/api/users/register">Sign Up</a>
</nav>

<h2>Book Your Tutor</h2>
<div>
    <div>1 <span>Select Subject</span></div>
    <div>2 <span>Choose Tutor</span></div>
    <div>3 <span>Pick Time</span></div>
    <div>4 <span>Payment</span></div>
    <div>5 <span>Confirm</span></div>
</div>

<!-- Step 1: Select Subject -->
<div id="step1">
    <div>
        <form:form modelAttribute="booking" method="post" action="${pageContext.request.contextPath}/api/bookings/search">
            <div>
                <label for="subject">Select Subject</label>
                <form:select path="subject">
                    <form:option value="" label="-- Select a subject --"/>
                    <c:forEach var="subject" items="${subjects}">
                        <form:option value="${subject.name}"/>
                    </c:forEach>
                </form:select>
                <p>Choose the subject you need tutoring for.</p>
            </div>
            <div>
                <label for="educationLevel">Education Level</label>
                <form:select path="educationLevel">
                    <form:option value="" label="-- Select education level --"/>
                    <form:option value="School"/>
                    <form:option value="College/University"/>
                    <form:option value="Professional Development"/>
                </form:select>
                <p>Select your current education level.</p>
            </div>
            <input type="submit" value="Find Tutors"/>
        </form:form>
    </div>
</div>

<!-- Step 2: Choose Tutor -->
<div id="step2" style="display:none;">
    <h3>Available Tutors</h3>
    <div>
        <c:forEach var="tutor" items="${tutors}">
            <div>
                <p>${tutor.fullName} - ${tutor.subjects}</p>
                <button onclick="selectTutor(${tutor.id})">Select</button>
            </div>
        </c:forEach>
    </div>
    <div>
        <button onclick="showStep('step1')">Back</button>
        <button onclick="showStep('step3')">Next</button>
    </div>
</div>

<!-- Step 3: Select Time Slot -->
<div id="step3" style="display:none;">
    <div>
        <form:form modelAttribute="booking" method="post" action="${pageContext.request.contextPath}/api/bookings/select-time">
            <div>
                <label for="sessionDate">Session Date</label>
                <form:input path="sessionDate" type="date" required="true"/>
                <p>Select the date for your tutoring session.</p>
            </div>
            <div>
                <label for="startTime">Available Time Slots</label>
                <form:select path="startTime">
                    <c:forEach var="slot" items="${tutor.availability}">
                        <form:option value="${slot.startTime}" label="${slot.startTime} - ${slot.endTime}"/>
                    </c:forEach>
                </form:select>
            </div>
            <div>
                <label for="duration">Session Duration</label>
                <form:select path="duration">
                    <form:option value="1" label="1 hour"/>
                    <form:option value="1.5" label="1.5 hours"/>
                    <form:option value="2" label="2 hours"/>
                </form:select>
                <p>Select the duration of your session.</p>
            </div>
            <div>
                <button type="button" onclick="showStep('step2')">Back</button>
                <button type="submit" onclick="showStep('step4')">Next</button>
            </div>
        </form:form>
    </div>
</div>

<!-- Step 4: Payment Information -->
<div id="step4" style="display:none;">
    <div>
        <%--@elvariable id="booking" type="ch"--%>
        <form:form modelAttribute="booking" method="post" action="${pageContext.request.contextPath}/api/bookings/payment">
            <div>
                <label>Payment Method</label>
                <form:select path="paymentMethod">
                    <form:option value="Credit Card"/>
                    <form:option value="PayPal"/>
                    <form:option value="Bank Transfer"/>
                </form:select>
            </div>
            <div id="creditCardDetails">
                <div>
                    <label>Card Number</label>
                    <form:input path="cardNumber"/>
                </div>
                <div>
                    <label>Expiry Date</label>
                    <form:input path="expiryDate"/>
                </div>
                <div>
                    <label>CVC</label>
                    <form:input path="cvc"/>
                </div>
                <div>
                    <label>Name on Card</label>
                    <form:input path="nameOnCard"/>
                </div>
            </div>
            <div id="paypalDetails" style="display:none;">
                <p>You will be redirected to PayPal to complete your payment after confirmation.</p>
            </div>
            <div id="bankTransferDetails" style="display:none;">
                <p>Please transfer the amount to our bank account using the following details:</p>
                <p>Bank Name: TuitionConnect Bank</p>
                <p>Account Number: 1234567890</p>
                <p>Routing Number: 987654321</p>
                <p>Amount: $${booking.total}</p>
                <p>Reference: Your booking ID</p>
            </div>
            <div>
                <h4>Order Summary</h4>
                <p>Tutor: ${tutor.fullName}</p>
                <p>Subject: ${booking.subject}</p>
                <p>Date: ${booking.sessionDate}</p>
                <p>Time: ${booking.startTime} - ${booking.endTime}</p>
                <p>Duration: ${booking.duration} hours</p>
                <p>Total: $${booking.total}</p>
            </div>
            <div>
                <button type="button" onclick="showStep('step3')">Back</button>
                <button type="submit" onclick="showStep('step5')">Continue</button>
            </div>
        </form:form>
    </div>
</div>

<!-- Step 5: Confirm Booking -->
<div id="step5" style="display:none;">
    <div>
        <h3>Confirm Your Booking</h3>
        <div>
            <h4>Tutor</h4>
            <p>${tutor.fullName} - ${tutor.bio}</p>
            <p>$${tutor.hourlyRate}/hour</p>
        </div>
        <div>
            <h4>Session Details</h4>
            <p>Date: ${booking.sessionDate}</p>
            <p>Time: ${booking.startTime} - ${booking.endTime}</p>
            <p>Duration: ${booking.duration} hours</p>
            <p>Payment Method: ${booking.paymentMethod}</p>
            <p>Total: $${booking.total}</p>
        </div>
        <div>
            <label for="specialRequests">Special Requests (Optional)</label>
            <form:textarea path="specialRequests" rows="5" cols="30"/>
        </div>
        <div>
            <button type="button" onclick="showStep('step4')">Back</button>
            <%--@elvariable id="booking" type="ch"--%>
            <form:form modelAttribute="booking" method="post" action="${pageContext.request.contextPath}/api/bookings">
                <input type="submit" value="Confirm Booking"/>
            </form:form>
        </div>
    </div>
</div>

<!-- Step 6: Booking Confirmed -->
<div id="step6" style="display:none;">
    <h3>Booking Confirmed!</h3>
    <p>Your tutoring session has been successfully booked. You'll receive a confirmation email with all the details shortly.</p>
    <a href="${pageContext.request.contextPath}/api/bookings/view?userId=${user.id}">View My Bookings</a>
</div>

<footer>
    <p>© 2025 TuitionConnect. All rights reserved.</p>
</footer>

<script>
    function showStep(stepId) {
        document.querySelectorAll('div[id^="step"]').forEach(step => step.style.display = 'none');
        document.getElementById(stepId).style.display = 'block';
    }

    function selectTutor(tutorId) {
        document.getElementById('booking_tutorId').value = tutorId;
        showStep('step3');
    }
</script>
</body>
</html>