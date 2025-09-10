@Library('shared') _
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
              dockerBuild()
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
                 trivyScan('fs')
            }
        }

        stage('Trivy Scan - Images') {
            steps {
                trivyScan('image')
            }
        }

        stage('Push to Docker Hub') {
            steps {
               dockerPush(DOCKER_HUB_CREDENTIALS, DOCKER_HUB_REPO)
                    

                    
                    }
                }
            }
        }



        stage('Build & Deploy with Tagged Images on vps') {
            steps {
                 deployApp(DOCKER_COMPOSE_FILE)
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
