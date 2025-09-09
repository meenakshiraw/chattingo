pipeline {
    agent any

    environment {
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
        BACKEND_IMAGE = "backend-image:latest"
        FRONTEND_IMAGE = "frontend-image:latest"
    }

    stages {
        stage('Git Clone') {
            steps {
                echo 'Cloning repository...'
                git branch: 'meenakshi', url: 'https://github.com/meenakshiraw/chattingo.git'
            }
        }

        stage('Image Build') { 
            steps {
                echo "Building Docker images..."

                // Build backend image
                sh "docker build -t ${BACKEND_IMAGE} ./backend"

                // Build frontend image
                sh "docker build -t ${FRONTEND_IMAGE} ./frontend"
            }       
        }

        stage('Filesystem Scan') {
            steps {
                echo 'Scanning source code for vulnerabilities...'

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
            post {
                always {
                    echo 'Cleaning Trivy cache...'
                    sh "rm -rf ${TRIVY_CACHE}"
                }
            }
        }

        stage('Docker Image Scan') {
            steps {
                echo 'Scanning Docker images for vulnerabilities...'

                sh '''
                #!/bin/bash
                set -e

                # Ensure Trivy is installed and in PATH
                export PATH=$WORKSPACE/bin:$PATH

                # Scan backend image
                trivy image --exit-code 1 --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" ${BACKEND_IMAGE} || true

                # Scan frontend image
                trivy image --exit-code 1 --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" ${FRONTEND_IMAGE} || true
                '''
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished. Cleaning workspace...'
            deleteDir()
        }
        failure {
            echo 'Pipeline failed due to build or security scan errors.'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
    }
}
