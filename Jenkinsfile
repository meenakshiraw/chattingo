@Library('shared') _

pipeline {
    agent any

    environment {
        DOCKER_HUB_REPO = "meenakshirawat/chattingo"
        DOCKER_HUB_CREDENTIALS = "docker_id"
        DOCKER_COMPOSE_FILE = "docker-compose.yaml"
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build Images') {
            steps {
                dockerBuild()
            }
        }

        stage('Trivy Scan - FS') {
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

        stage('Deploy on VPS') {
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
