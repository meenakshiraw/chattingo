 pipeline {
    agent any

    environment {
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
        DOCKER_HUB_REPO = "docker_id/chattingo" // Docker Hub repo
        DOCKER_HUB_CREDENTIALS = "docker_id"    // Jenkins credential ID
        DOCKER_COMPOSE_FILE = "docker-compose.yaml"
    }

    stages {

       
       stage('Checkout') {

            
            steps {

                // Option 1: checkout from the repo that triggered the pipeline
                checkout scm
                // This pulls code from your GitHub repo into Jenkins workspace
                git branch: 'meenakshi',
                    url: 'https://github.com/meenakshiraw/chattingo.git'
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
                      // Use the image names from the build stage
                     sh "docker tag backend-image:latest  meenakshirawat/chattingo:backend-latest"
                     sh "docker tag frontend-image:latest  meenakshirawat/chattingo:frontend-latest"

                     sh "docker push   meenakshirawat/chattingo:backend-latest"
                     sh "docker push   meenakshirawat/chattingo:frontend-latest"
                    

                    
                    }
                }
            }
        }



        stage('Build & Deploy with Tagged Images on vps') {
            steps {
                script {
                    // Set tag using Jenkins build number
                    def IMAGE_TAG = "build-${env.BUILD_NUMBER}"
                    echo "Using image tag: ${IMAGE_TAG}"

                    // Update docker-compose.yml with new image tag
                    sh """
                    sed -i 's|chattingo-frontend:.*|chattingo-frontend:${IMAGE_TAG}|' ${DOCKER_COMPOSE_FILE}
                    sed -i 's|chattingo-backend:.*|chattingo-backend:${IMAGE_TAG}|' ${DOCKER_COMPOSE_FILE}
                    cat docker-compose.yaml
                    """
                    
                //Make sure docker-compose is executable
                   // sh "chmod +x /usr/local/bin/docker-compose"

                //Run docker-compose up with build
                    sh "/usr/local/bin/docker-compose -f docker-compose.yaml up --build -d"

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
