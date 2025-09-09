pipeline {
    agent any

    environment {
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
        DOCKER_IMAGE = "backend-image:latest"
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
                sh 'docker build -t backend-image:latest ./backend'
                sh 'docker build -t frontend-image:latest ./frontend'
            }       
        }

        stage('Filesystem Scan') {
            steps {
                echo 'Scanning source code for vulnerabilities...'
                sh '''
                    mkdir -p "$TRIVY_CACHE"

                    # Run filesystem scan, do not fail pipeline
                    trivy fs --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" . || true
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
                echo 'Scanning Docker image for vulnerabilities...'
                sh '''
                    mkdir -p "$TRIVY_CACHE"

                    # Scan Docker image, do not fail pipeline
                    trivy image --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" ${DOCKER_IMAGE} || true
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
            echo 'Pipeline failed.'
        }
        success {
            echo 'Pipeline completed successfully.'
        }
    }
}
