# tutor_buddy

![image](https://github.com/user-attachments/assets/7ebcf3f6-0eb3-454b-b693-2057402a9472)
# TuitionConnect - Home Tutor Booking and Searching System

## Overview
TuitionConnect is a web application that connects students with tutors for personalized academic success. Students can search for tutors by subject, book sessions, and manage their bookings, while tutors can register and manage their profiles.

## Features
- User registration and login (students and tutors)
- Search and book tutors by subject
- View and manage bookings (upcoming, completed, cancelled)
- Tutor management (add/edit tutors)
- Profile management (view/edit/delete user profiles)

## Tech Stack
- **Front-End**: JSP, Tailwind CSS
- **Back-End**: Spring Boot, Java
- **Data Structure**: Binary Search Tree (BST) for tutor storage, Merge Sort for sorting tutors by subject
- **Persistence**: File-based storage (`users.txt`, `bookings.txt`)

## Setup Instructions
1. Clone the repository: `git clone <repo-url>`
2. Open in IntelliJ IDEA.
3. Ensure Java 17 is installed.
4. Run the application (`TuitionConnectApplication.java`).
5. Access at `http://localhost:8080`.

## File Structure
- `src/main/java/com/tuitionconnect/`: Java source code (controllers, services, models)
- `src/main/webapp/WEB-INF/views/`: JSP files for the front-end
- `users.txt`, `bookings.txt`: Data storage files

## Usage
- Register as a student or tutor.
- Log in to access features.
- Search for tutors, book sessions, and manage your bookings/profile.

## Future Improvements
- Integrate a database (e.g., MySQL) for better persistence.
- Add payment gateway integration.
- Implement real-time notifications for booking confirmations.