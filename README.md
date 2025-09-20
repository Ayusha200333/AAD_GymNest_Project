 # 🏋️‍♀️ GymNest Management System 
 
 ## 📌 Project Description

**GymNest** is a complete fitness center management system developed as part of the Advanced Application Development Project.  
It provides an **admin panel** to manage members, guides, classes, memberships, bookings, payments, attendance, and dashboards with visual statistics.

## 🚀 Features
The purpose of this project is to create a **web-based gym management system** that allows admin users to:

✅ **Member Management** – Register, update, search, delete members  
✅ **Guide Management** – Add/edit trainers, set status, upload profile images  
✅ **Class Scheduling** – Create, update, delete classes (day/time/guide)  
✅ **Membership Packages** – Plans with classes, pricing & images  

⭐ **Locations** – Manage gym branches with images  
⭐ **Bookings** – Assign members to packages/classes, track sessions  
⭐ **Payments** – Record & view payment history (secure card masking)  
⭐ **Attendance** – Member check-in / check-out with daily logs  

📌 **Reviews** – Collect & moderate member feedback  
📌 **Search & Filters** – Global search across all modules  
📌 **Image Uploads** – For guides, memberships & locations (with preview)  
🔒 **Authentication** – JWT-based secure login  
🛠️ **CRUD Support** – Full create, read, update, delete operations  

## 🖼️ Screenshots

### 🔹 Signin Page
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/d13518d9-5435-44d9-93f0-f1ea10a6f2ae" />

### 🔹 Signup Page
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/da1af2c3-e446-489f-8396-c7083f6a578d" />

### 🔹 Index 
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/932bebdf-406e-48c7-8a4e-d725bde6b58b" />

### 🔹 Member Management 
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/2c975040-6b17-49a8-bfc3-6f187a6fd97e" />

### 🔹 Payment Records
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/b31b06e5-d6f1-436d-bba6-0c5154f8e76a" />

### 🔹 Guide Management
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/bbd11f31-f05a-4094-9f3b-60800f83952a" />

### 🔹 Use Bokking Section
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/4ed1ae20-02be-47ef-b2c0-65f560151d7b" />

### 🔹 User Review Section
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/603581dd-cbd7-4d02-9d76-ccd5f092ea29" />

### 🔹 User Profile Section
<img width="1366" height="768" alt="image" src="https://github.com/user-attachments/assets/32b830ee-2c02-4ac5-96fb-200492682f65" />


## ⚙️ Setup Instructions

### 1️⃣ Clone Repository
```bash

git clone https://github.com/Ayusha200333/AAD_GymNest_Project.git
cd AAD_GymNest_Project

2️⃣ Backend Setup (Spring Boot + MySQL)
Requirements:
Java 17+
Maven
MySQL
Steps:
Import the backend project into your IDE (IntelliJ / Eclipse / VS Code).
Create a MySQL database:
SQL

CREATE DATABASE gymnest_db;
Update application.properties (inside backend/src/main/resources) with your DB credentials:
properties

spring.datasource.url=jdbc:mysql://localhost:3306/gymnest_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
jwt.secret=yourSecretKey
Run the Spring Boot application:
Bash

mvn spring-boot:run
Backend will run at:
👉 http://localhost:8080/api/v1/...

3️⃣ Frontend Setup (Admin Panel)
Requirements:
A web server (or simply run with VS Code Live Server / IntelliJ HTTP server).
Browser (Chrome/Firefox recommended).
Steps:
Navigate to the frontend directory:
Bash

cd frontend/admin-panel
Open admin-panel.html in browser OR run using Live Server (VS Code).
Login with the provided credentials (after backend is seeded with data).
Admin can now navigate through Dashboard, Members, Guides, Classes, etc.

4️⃣ Login and Authentication
The backend uses JWT for login/authentication.
On successful login, tokens are saved to localStorage in the frontend.
All authorized API calls attach headers automatically:
JSON
Authorization: Bearer <token>

👨‍💻 Author
Developed by Ayusha Wijerathna– Advanced Application Development Project (AAD).

 



