pipeline {
    agent any

    stages {
        stage('Git Clone') { 
            steps {
                echo "Cloning repository from GitHub..."
                git branch: 'meenakshi', url: 'https://github.com/meenakshiraw/chattingo.git'
            }
        }

        stage('Image Build') { 
            steps {
                echo "Building Docker images of backend..."
                // Build backend image
                sh './backend/docker build -t backend-image:latest ./backend'
                
                // Build frontend image
                sh './frontend/docker build -t frontend-image:latest ./frontend'
            }
        }
    }
}
