# 🚀 Chattingo - Mini Hackathon Challenge

A full-stack real-time chat application built with React, Spring Boot, and WebSocket technology. Containerize this application using Docker and deploy it to Hostinger VPS using Jenkins CI/CD pipeline.



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


---

# 🚀 Project: Containerized Full-Stack Application

## 📌 Overview

This project demonstrates a complete containerized setup for a **React frontend**, **Spring Boot backend**, and **MySQL database**, orchestrated using **Docker Compose**, automated with **Jenkins CI/CD pipeline**, and served through **Nginx** as a reverse proxy with HTTPS support.

The stack includes:

* **Docker** → Application containerization
* **Docker Compose** → Multi-container orchestration
* **Jenkins** → CI/CD pipeline automation
* **Nginx** → Web server & reverse proxy

---

## 🛠️ Technologies Used

* **Frontend** → React (served via Nginx)
* **Backend** → Spring Boot (Java)
* **Database** → MySQL (Dockerized)
* **Containerization** → Docker & Docker Compose
* **Automation** → Jenkins Pipeline
* **Web Server** → Nginx (with SSL support)

---

## 📂 Project Structure

```
.
├── frontend/                 # React app with Dockerfile (multi-stage build)
│   └── Dockerfile
├── backend/                  # Spring Boot app with Dockerfile (multi-stage build)
│   └── Dockerfile
├── nginx/                    # Nginx config for reverse proxy & SSL
│   └── nginx.conf
├── docker-compose.yml        # Orchestration file
└── README.md
```

---

## ⚡ Setup Instructions

### 1️⃣ Clone Repository

```bash
git clone https://github.com/meenakshiraw/chattingo.git
cd chattingo
```

### 2️⃣ Build & Run with Docker Compose

```bash
docker-compose up --build -d
```

* Frontend → [http://meenakshirawat.net]
* Backend → [http://meenakshirawat.9090]
* Jenkins → [http://meenakshirawat.net:8080]
* MySQL → [localhost:3306]

### 3️⃣ Access Jenkins

* Open: http://meenakshirawat.net:8080
* Default admin password (inside container):

```bash
docker exec -it jenkins cat /var/jenkins_home/secrets/initialAdminPassword
```

---

## 🔄 Jenkins CI/CD Pipeline

* **Stage 1**: Checkout code from GitHub
* **Stage 2**: Build & test backend with Maven
* **Stage 3**: Build React frontend
* **Stage 4**: Build & push Docker images to Docker Hub
* **Stage 5**: Deploy services using Docker Compose

---

## 🌐 Nginx Configuration

* Acts as **reverse proxy** for frontend & backend
* Supports **SSL/TLS certificates** (self-signed or Certbot)
* Routes:

  * `/` → React frontend
  * `/api` → Spring Boot backend

Example snippet from `nginx.conf`:

```nginx
server {
    listen 80;
    server_name meenakshirawat.net www.meenakshirawat.net;

    location / {
        root /usr/share/nginx/html;
        index index.html;
    }

    location /api/ {
        proxy_pass http://backend:8080/;
    }
}
```

---

## 🔒 SSL Setup

* Generate a self-signed SSL certificate:

```bash
openssl req -x509 -nodes -days 365 -newkey rsa:2048 \
-keyout nginx/ssl/self.key -out nginx/ssl/self.crt
```

* Update `nginx.conf`:

```nginx
ssl_certificate /etc/nginx/ssl/self.crt;
ssl_certificate_key /etc/nginx/ssl/self.key;
```

---

## ✅ Deliverables

* **Docker**: Multi-stage builds for frontend & backend
* **Docker Compose**: Multi-container orchestration
* **Jenkins**: Automated CI/CD pipeline
* **Nginx**: Reverse proxy with SSL

---



