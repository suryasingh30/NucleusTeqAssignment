# Event Management Platform 🎟️  
[![License](https://img.shields.io/badge/license-MIT-blue.svg)](LICENSE)  
[![Java](https://img.shields.io/badge/Java-17-orange)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.1.x-brightgreen)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue)](https://www.mysql.com/)

A secure Spring Boot application for event organizers and attendees, featuring JWT authentication, role-based access, and real-time seat management.

---

## ✨ Key Features  
### 👨‍💻 **Organizers**  
- Create/edit/cancel events with titles, dates, categories (Conference/Workshop/Seminar), and capacity limits.  
- View bookings and generate reports (e.g., "85/100 seats booked").  
- Automatic closure of bookings when max capacity is reached.  

### 🎫 **Attendees**  
- Browse available events with filters (date/category).  
- Book tickets and view booking history/status.  
- Cancel bookings (optional feature).  

### 🔒 **Security**  
- JWT authentication with token expiration.  
- Password encryption via BCrypt.  
- Role-based API access (e.g., only organizers can create events).  

---

## 🛠️ Tech Stack  
| Component       | Technology               |
|-----------------|--------------------------|
| Backend         | Spring Boot 3.1 (Java 17)|
| Database        | MySQL 8.0                |
| Authentication | Spring Security + JWT    |
| Build Tool      | Maven 3.8.x              |

---

## 🚀 Quick Start  

### Prerequisites  
- Java 17  
- MySQL 8.0  
- Maven 3.8+  

### Setup  
1. **Clone & Navigate**:  
   ```bash
   git clone https://github.com/suryasingh30/NucleusTeqAssignment.git
   cd NucleusTeqAssignment/eventmanagement
