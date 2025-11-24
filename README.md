# 🎓 Student Information System

This repository contains the **full-stack backend server** for a Student Information System (SIS), built as an intern project to learn modern application development and deployment practices.

---

## ⚙️ Core Technology Stack

| Component  | Technology               | Details |
|-----------|---------------------------|---------|
| Backend   | Java 17, Spring Boot 3.4  | RESTful API services (Controller, Service, Repository layers). |
| Database  | AWS RDS (MySQL 8)         | Persistent, managed cloud database. |
| Security  | JWT Authentication        | Stateless token-based security with RBAC. |
| Deployment| AWS Elastic Beanstalk     | Single-file JAR deployment on Free Tier EC2. |
| Build Tool| Maven                     | Dependency management and JAR creation. |
| Testing   | Postman                   | Automated API testing for full workflow. |

---

## 📋 Key Project Features

### **Student & Profile**
- Secure Login/Registration  
- View & modify user profile  
- View enrolled courses and status (Pending/Approved)

### **Course & Enrollment**
- View available courses (all logged-in users)  
- Request enrollment  
- Admin-only CRUD for course management  

### **Admin Control (RBAC)**
- **Admin Role** with full course lifecycle permissions  
- Approve/Reject student enrollment requests  

---

## 🚀 Live API Endpoint

Base URL for all server requests:
http://Student-system-env.eba-24yuymwa.us-west-2.elasticbeanstalk.com/api/v1


---

## 🛠️ Local Development & Setup

### **1. Prerequisites**
- JDK 17+  
- Maven  
- Eclipse/IntelliJ  
- AWS RDS (MySQL) credentials  

---

### **2. Critical IDE Environment Variable Setup**

To run the project locally:

1. Right-click `ServerApplication.java`  
2. Select **Run As → Run Configurations…**  
3. Open the **Environment** tab  
4. Add the following variables:

- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- JWT_SECRET_KEY

Use the **same values as in AWS Elastic Beanstalk → Configuration → Environment Properties**.

---

### **3. Build the Application**

Build JAR while skipping tests:

```sh
mvn clean package -DskipTests

