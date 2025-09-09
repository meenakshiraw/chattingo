pipeline {
    agent any

    environment {
        TRIVY_CACHE = "${WORKSPACE}/.trivycache"
        BACKEND_IMAGE = "backend-image:latest"
        FRONTEND_IMAGE = "frontend-image:latest"
        GIT_SSH_CREDENTIALS_ID = "github-ssh" // Jenkins SSH credentials ID
        REPO_URL = "git@github.com:meenakshiraw/chattingo.git"
        BRANCH = "meenakshi"
    }

    stages {
        stage('Git Clone') {
            steps {
                echo 'Cloning repository using SSH...'
                git branch: "${BRANCH}", url: "${REPO_URL}", credentialsId: "${GIT_SSH_CREDENTIALS_ID}"
            }
        }


    }
}
