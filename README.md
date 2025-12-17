## ✈️ Flight Management System (NetBeans)

A simple **Flight Booking System** built using **Java Swing (NetBeans)** and **MySQL**.
This desktop application allows users to register, search flights, book tickets, and view previous bookings. Admins can manage flights and view customer details.

> **GitHub Repository**: [Flight_Management_System__NetBeans](https://github.com/Dhruv-Tikhande/Flight_Management_System__NetBeans)

---

## 🚀 Features

### 👤 User Module

* User registration with basic details (name, phone, email, username, Aadhaar, password)
* User login with database validation
* Search flights by source and destination
* Book flights and generate a boarding pass
* View previous bookings (cancelled bookings filtered)
* Edit profile details (name, email, phone)

### 🛠️ Admin Module

* Admin login (credentials stored in MySQL)
* Admin dashboard to:

  * Add new flights
  * Edit / update flight details
  * View customer details
  * Navigate between management screens

### ⚙️ General

* Simple and intuitive Java Swing GUI
* MySQL database for persistent storage
* Core logic written in plain Java (no external frameworks)

---

## 🧰 Tech Stack

* **Language**: Java (JDK 8+)
* **GUI**: Java Swing (NetBeans GUI Builder)
* **Database**: MySQL
* **Build Tool**: Ant (`build.xml`)
* **IDE**: NetBeans (recommended)

---

## 📁 Project Structure

```
src/
 └── flight_booking_system/
     ├── MainGUI.java                # Main landing screen
     ├── RegistrationForm.java       # User registration
     ├── LoginForm.java              # User login
     ├── UserFlightSearch.java       # Flight search
     ├── BookingForm.java            # Booking screen
     ├── BoardingPassFrame.java      # Boarding pass
     ├── PreviousBookingsFrame.java  # Booking history
     ├── EditProfileFrame.java       # Edit user profile
     ├── AdminLoginForm.java         # Admin login
     ├── AdminPanel.java             # Admin dashboard
     ├── AdminPanel1.java            # Admin dashboard (alt)
     ├── AddFlightForm.java          # Add flights
     ├── EditFlightForm.java         # Edit flights
     ├── customerDetails.java        # Customer details
     ├── FlightTableModel.java       # Table model helper
     └── DatabaseConnection.java     # DB connection helper

src/Images/                            # UI images & icons
build.xml                              # Ant build script
nbproject/                             # NetBeans project metadata
```

---

## 🗄️ Database Setup

The application connects to a local MySQL database using the following configuration (inside `DatabaseConnection.java`):

```java
String url = "jdbc:mysql://localhost:3306/flight_management";
String user = "root";
String password = "1234";
```

> Update these values according to your MySQL setup.

---

### 1️⃣ Create Database

```sql
CREATE DATABASE IF NOT EXISTS flight_management;
USE flight_management;
```

### 2️⃣ Create Tables

```sql
-- Users table
CREATE TABLE users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    phone_no VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    aadhar_card_no VARCHAR(20) NOT NULL
);

-- Admin table
CREATE TABLE admin (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL
);

-- Flights table
CREATE TABLE flights (
    id INT AUTO_INCREMENT PRIMARY KEY,
    flight_id VARCHAR(20) NOT NULL UNIQUE,
    source VARCHAR(100) NOT NULL,
    destination VARCHAR(100) NOT NULL,
    departure_time VARCHAR(50),
    arrival_time VARCHAR(50),
    price DECIMAL(10,2)
);

-- Bookings table
CREATE TABLE bookings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(100) NOT NULL,
    phone_no VARCHAR(20) NOT NULL,
    email VARCHAR(100) NOT NULL,
    flight_id VARCHAR(20) NOT NULL,
    seat_no VARCHAR(20),
    total_amount DECIMAL(10,2),
    username VARCHAR(50) NOT NULL,
    source VARCHAR(100),
    destination VARCHAR(100),
    booking_date DATE,
    cancelled TINYINT(1) DEFAULT 0
);
```

---

### 3️⃣ Insert Default Admin

```sql
INSERT INTO admin (username, password)
VALUES ('admin', 'admin123');
```

**Admin Credentials**

* Username: `admin`
* Password: `admin123`

---

## ▶️ Running the Project

### ✅ Option A: NetBeans (Recommended)

1. Open **NetBeans**
2. Go to **File → Open Project**
3. Select the project folder
4. Ensure **MySQL** is running and DB is configured
5. Right‑click project → **Clean and Build**
6. Right‑click project → **Run**

---

### ✅ Option B: Command Line

```bash
cd Flight_Management_System__NetBeans
java -cp build/classes flight_booking_system.MainGUI
```

> Ensure MySQL JDBC driver is available in classpath.

---

## 🔐 Credentials Summary

### Admin

* Username: `admin`
* Password: `admin123`

### User

* Register using **New User** button
* Login with the same credentials

---

## 🖼️ Screenshots (Optional)

```markdown
![Main Screen](screenshots/main_gui.png)
![Admin Panel](screenshots/admin_panel.png)
```

---

## 🔮 Future Improvements

* Password hashing (security enhancement)
* Better input validation
* Advanced seat selection system
* Booking & revenue reports
* Export as executable JAR / installer

---

## 📄 License

This project is currently **unlicensed**.
You may add an MIT or other open‑source license if needed.
