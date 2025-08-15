# 📚 PrintXchange – College Stationery Shop Management System

## 📌 Overview
**PrintXchange** is a role-based web platform designed to streamline college stationery shop operations.  
It enables students and faculty to **place print orders online**, **exchange used items**, and **track order status in real time** — reducing physical crowding and waiting times at the shop.

The platform ensures **secure access**, **seamless order processing**, and a **mobile-friendly user interface**, making it convenient for both shop managers and customers.

---

## 🚀 Features
- **Role-Based Access**  
  Separate functionalities for Admin, Staff, and Users.
- **Online Print Order Placement**  
  Submit printing requests with PDF file uploads.
- **Used Item Exchange**  
  Post and browse second-hand items for exchange.
- **Order Status Tracking**  
  Real-time updates on print orders and exchanges.
- **Secure Authentication**  
  Login with Spring Security and CSRF protection.
- **Responsive UI**  
  Mobile-friendly design for accessibility.
- **Session Handling**  
  Maintains secure and smooth user experience.

---

## 🛠 Tech Stack
- **Backend:** Spring Boot 3.5.3, Java 17  
- **Frontend:** Thymeleaf, HTML5, CSS3, JavaScript  
- **Database:** MySQL  
- **Security:** Spring Security, CSRF Protection  
- **Build Tool:** Maven  
- **Others:** Lombok, DevTools  

---

## 📂 Project Structure
PrintXchange/
├── src/main/java/com/example/printxchange
│ ├── controllers/ # Web controllers
│ ├── models/ # Entity classes
│ ├── services/ # Business logic
│ ├── repositories/ # Data access layer
│ └── PrintXchangeApplication.java
├── src/main/resources/
│ ├── templates/ # Thymeleaf HTML templates
│ ├── static/ # CSS, JS, images
│ ├── application.properties
├── pom.xml


---

## ⚙️ Prerequisites
- **Java 17** or higher  
- **Maven 3.8+**  
- **MySQL** (create a database and update credentials in `application.properties`)

---

## 🔧 Installation & Setup
1. **Clone the repository**
   ```bash
   git clone https://github.com/HeisenBerg-872/Stationary-Management.git
   cd Stationary-Management
🛡 Security
Spring Security handles authentication and authorization.

CSRF protection is enabled

Session management ensures secure access control.
