pipeline {
    agent any

    environment {
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
        DOCKER_HUB_REPO = "docker_id/chattingo" // Docker Hub repo
        DOCKER_HUB_CREDENTIALS = "docker_id"    // Jenkins credential ID
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
        stage('Trivy Filesystem Scan') {
            steps {
                echo 'Scanning source code for vulnerabilities...'
                sh '''
                    mkdir -p "$TRIVY_CACHE"
                    trivy fs --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" . || true
                '''
            }
        }

        stage('Trivy Docker Image Scan') {
            steps {
                echo 'Scanning Docker images for vulnerabilities...'
                sh """
                    mkdir -p "$TRIVY_CACHE"
                    trivy image --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" ${env.BACKEND_IMAGE_TAG} || true
                    trivy image --severity HIGH,CRITICAL --cache-dir "$TRIVY_CACHE" ${env.FRONTEND_IMAGE_TAG} || true
                """
            }
        }

        stage('Push to Docker Hub') {
            steps {
                echo 'Pushing Docker images to Docker Hub...'
                script {
                    docker.withRegistry('https://index.docker.io/v1/', DOCKER_HUB_CREDENTIALS) {
                        sh "docker push ${env.BACKEND_IMAGE_TAG}"
                        sh "docker push ${env.FRONTEND_IMAGE_TAG}"
                    }
                }
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
