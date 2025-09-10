# 🚀 Chattingo - Mini Hackathon Challenge

A full-stack real-time chat application built with React, Spring Boot, and WebSocket technology. **Your mission**: Containerize this application using Docker and deploy it to Hostinger VPS using Jenkins CI/CD pipeline.



## 🏗️ Architecture Overview

```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Backend       │    │   Database      │
│   (React)       │◄──►│   (Spring Boot) │◄──►│   (MySQL)       │
│   Port: 80      │    │   Port: 8080    │    │   Port: 3306    │
└─────────────────┘    └─────────────────┘    └─────────────────┘
         │                       │
         └────── WebSocket ──────┘
```

## 🛠️ Technology Stack

### Frontend
- **React 18** - Modern UI framework
- **Redux Toolkit** - State management
- **Material-UI** - Component library
- **Tailwind CSS** - Utility-first CSS
- **WebSocket (SockJS + STOMP)** - Real-time messaging
- **React Router** - Client-side routing

### Backend
- **Spring Boot 3.3.1** - Java framework
- **Spring Security** - Authentication & authorization
- **Spring Data JPA** - Database operations
- **Spring WebSocket** - Real-time communication
- **JWT** - Token-based authentication
- **MySQL** - Database

### DevOps (Your Tasks)
- **Docker** - Containerization (YOU BUILD)
- **Docker Compose** - Multi-container orchestration (YOU BUILD)
- **Jenkins** - CI/CD pipeline (YOU BUILD)
- **Nginx** - Web server & reverse proxy (YOU BUILD)


### **Task 1: Docker Implementation (5 Marks)**

You need to create these files from scratch:

#### **Frontend Dockerfile** (3-stage build)
- Stage 1: Node.js build environment
- Stage 2: Build React application  
- Stage 3: Nginx runtime server

#### **Backend Dockerfile** (3-stage build)
- Stage 1: Maven build environment
- Stage 2: Build Spring Boot application
- Stage 3: JRE runtime

#### **Docker Compose** (Root level)
Create `docker-compose.yml` to orchestrate all services.

**Scoring**: Single Stage (2), Two Stage (4), Multi Stage (5)

### **Task 2: Jenkins CI/CD Pipeline (17 Marks)**

Create a `Jenkinsfile` with these stages:

```groovy
pipeline {
    agent any
    
    stages {
        stage('Git Clone') { 
            // Clone repository from GitHub (2 Marks)
        }
        stage('Image Build') { 
            // Build Docker images for frontend & backend (2 Marks)
        }
        stage('Filesystem Scan') { 
            // Security scan of source code (2 Marks)
        }
        stage('Image Scan') { 
            // Vulnerability scan of Docker images (2 Marks)
        }
        stage('Push to Registry') { 
            // Push images to Docker Hub/Registry (2 Marks)
        }
        stage('Update Compose') { 
            // Update docker-compose with new image tags (2 Marks)
        }
        stage('Deploy') { 
            // Deploy to Hostinger VPS (5 Marks)
        }
    }
}
```

### Additional Requirements
- **Jenkins Shared Library**: 3 Marks
- **Active Engagement**: 2 Marks
- **Creativity**: 2 Marks
- **Quality Storytelling**: 10 Marks
  - README (Compulsory): 3 Marks
  - Blog (Optional): 2 Marks
  - Video (Compulsory): 5 Marks

### **Task 3: VPS Deployment**
- **Hostinger VPS Setup**: Ubuntu 22.04 LTS, 2GB RAM
- **Domain Configuration**: Setup your domain with DNS
- **SSL Certificate**: Configure HTTPS with Let's Encrypt
- **Production Deployment**: Automated deployment via Jenkins


## 📱 Application Features

### Core Functionality
- ✅ User authentication (JWT)
- ✅ Real-time messaging (WebSocket)
- ✅ Group chat creation
- ✅ User profile management
- ✅ Message timestamps
- ✅ Responsive design

### API Endpoints
```
POST   /api/auth/register    - User registration
POST   /api/auth/login       - User login
GET    /api/users            - Get users
POST   /api/chats/create     - Create chat
GET    /api/chats            - Get user chats
POST   /api/messages/create  - Send message
GET    /api/messages/{chatId} - Get chat messages
WS     /ws                   - WebSocket endpoint
```

## 📊 Project Structure

```
chattingo/
├── backend/                 # Spring Boot application
│   ├── src/main/java/
│   │   └── com/chattingo/
│   │       ├── Controller/  # REST APIs
│   │       ├── Service/     # Business logic
│   │       ├── Model/       # JPA entities
│   │       └── config/      # Configuration
│   ├── src/main/resources/
│   │   └── application.properties
│   ├── .env                 # Environment variables
│   └── pom.xml
├── frontend/               # React application
│   ├── src/
│   │   ├── Components/     # React components
│   │   ├── Redux/          # State management
│   │   └── config/         # API configuration
│   ├── .env                # Environment variables
│   └── package.json
├── CONTRIBUTING.md         # Detailed setup & deployment guide
└── README.md              # This file
```



### **Required Submission Fields**
1. **Name** - Your full name
2. **Email ID** - Contact email
3. **GitHub Repository URL** - Your forked and implemented project
4. **Video Demo URL** - 3-minute demo video (YouTube/Drive link)
5. **Live Application URL** - Your deployed application on VPS
6. **Blog URL** - Technical writeup (Optional but recommended)
7. **README URL** - Link to your updated README file

