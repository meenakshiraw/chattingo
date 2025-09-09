pipeline {
    agent any

    stages {


        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace...'
                deleteDir()  // This wipes the entire workspace
            }
        }

        stage('Git Clone') { 
            
            steps {
                echo "Cloning repository from GitHub..."
                git branch: 'meenakshi', url: 'https://github.com/meenakshiraw/chattingo.git',poll: false
            }
        }

        
        stage('Image Build') { 
            steps {
                echo "Building Docker images of backend..."
                // Build backend image
                sh 'docker build -t backend-image:latest ./backend'
                
                // Build frontend image
                sh 'docker build -t frontend-image:latest ./frontend'
            }       
        }
    }
}
