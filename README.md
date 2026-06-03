
Architecture
C4 Level 2 Container Diagram
<img width="1840" height="2428" alt="LMS Container Diagram" src="https://github.com/user-attachments/assets/f45c412a-60aa-4376-92bc-6a5dc3c99d16" />

Tech Stack
Language: Java 21
Framework: Spring Boot 4.0.6
Build Tool: Maven
Database: (Configured in application.properties)
Getting Started
Prerequisites
Java Development Kit (JDK) 21 or higher
Maven
Installation
Clone the repository

git clone https://github.com/Thagika/lms_backend.git/
cd lms-backend
Build the project

./mvnw clean install
Run the application

./mvnw spring-boot:run
The application will start on the default port (usually 8080).

# Learning Management System (LMS) - Backend

## Overview

This repository contains the backend code for a Learning Management System designed for university environments.
The system facilitates interaction between students and lecturers, streamlining the academic process from course enrollment to grading and information sharing.

## Features

### 👥 User Management
-**Registration**: Secure sign-in/sign-up process for both Students and Lecturers.
-**Authentication**: Role-based login system (Student/Lecturer) with secure password handling.
-**Profiles**: Persistent user profiles for personalized dashboards.


### 📚 Course Management
- **Course Catalog:** Students can browse available university courses with details (description, instructor, schedule).
- **Enrollment:** One-click enrollment for students.
- **Class Rosters:** Teachers can view lists of all courses they teach and the students enrolled in them.

### 📝 Grading & Assessment
-**regards to repeating exams:** the students can only make an max of 3 attempts to face the exam (this was done to avoid having to create a seperate table just for grades when the user repeats exams)
- **Teacher Tools:** Interface for teachers to input marks for assignments, tests, and modules.
- **Student Portal:** Dedicated grades page for students to track their academic performance across all courses.

### 📢 Communication & Resources
- **Announcements:** Teachers can post updates which trigger automatic email notifications to enrolled students.
- **Study Materials:** Centralized repository for teachers to upload lecture notes, assignments, and reading resources.

## Architecture

### C4 Level 2 Container Diagram
![C4 Level 2 Container Diagram](<img width="1840" height="2428" alt="LMS Container Diagram" src="https://github.com/user-attachments/assets/fc020d5b-5e95-4d1c-8f91-6fee9ad93bf9" />
)

## Tech Stack

- **Language:** Java 21
- **Framework:** Spring Boot 4.0.6
- **Build Tool:** Maven
- **Database:** (Configured in `application.properties`)

## Getting Started

### Prerequisites
- Java Development Kit (JDK) 21 or higher
- Maven

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/Thagika/LMS_system.git
   cd lms-backend
   ```

2. **Build the project**
   ```bash
   ./mvnw clean install
   ```

3. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

The application will start on the default port (usually 8080).
