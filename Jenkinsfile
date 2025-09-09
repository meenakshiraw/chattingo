pipeline {
    agent any
    environment {
        DOCKER_IMAGE = "backend-image:latest"
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
    }
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
        stage('Filesystem Scan') {
           steps {
             echo 'Scanning source code for vulnerabilities...'

             // Make sure TRIVY_CACHE is set
            script {
              env.TRIVY_CACHE = "${env.WORKSPACE}/.trivycache"
            }

            sh '''
            #!/bin/bash
            set -e

            # Install Trivy locally if not already installed
            if [ ! -f "$WORKSPACE/bin/trivy" ]; then
                 echo "Installing Trivy..."
                 mkdir -p "$WORKSPACE/bin"
                 curl -sfL https://raw.githubusercontent.com/aquasecurity/trivy/main/contrib/install.sh | sh -s -- -b "$WORKSPACE/bin"
        fi

            # Add Trivy to PATH
            export PATH=$WORKSPACE/bin:$PATH

            # Verify installation
            trivy --version

            # Run filesystem scan
            trivy fs --exit-code 1 --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" .
            '''
        }

        stage('Image Scan') {
            steps {
                echo "Scanning Docker images for vulnerabilities..."
                sh "trivy image --exit-code 1 --severity HIGH,CRITICAL ${DOCKER_IMAGE_BACKEND}"
                sh "trivy image --exit-code 1 --severity HIGH,CRITICAL ${DOCKER_IMAGE_FRONTEND}"
            }
        }

    }

    post {
        always {
            echo "Cleaning Trivy cache..."
            sh "rm -rf ${TRIVY_CACHE}"
        }
        success {
            echo "Pipeline completed successfully!"
        }
        failure {
            echo "Pipeline failed due to build or security scan errors."
        }
    }
}
