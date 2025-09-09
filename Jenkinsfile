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
                sh "docker build -t ${BACKEND_IMAGE} ./backend"
                sh "docker build -t ${FRONTEND_IMAGE} ./frontend"
            }
        }

        stage('Filesystem Scan') {
            steps {
                echo 'Scanning source code for vulnerabilities...'
                sh """
                trivy fs --exit-code 1 --severity HIGH,CRITICAL --cache-dir ${TRIVY_CACHE} .
                """
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
                sh """
                trivy image --exit-code 1 --severity HIGH,CRITICAL --cache-dir ${TRIVY_CACHE} ${BACKEND_IMAGE} || true
                trivy image --exit-code 1 --severity HIGH,CRITICAL --cache-dir ${TRIVY_CACHE} ${FRONTEND_IMAGE} || true
                """
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
