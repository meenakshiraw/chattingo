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

        stage('Image Build') {
            steps {
                echo "Building Docker images..."
                sh "docker build -t ${BACKEND_IMAGE} ./backend"
                sh "docker build -t ${FRONTEND_IMAGE} ./frontend"
            }
        }

        stage('Filesystem Scan') {
            steps {
                echo '🔍 Scanning source code for vulnerabilities...'
                sh """
                    mkdir -p ${TRIVY_CACHE}
                    trivy fs --exit-code 1 --severity HIGH,CRITICAL --cache-dir ${TRIVY_CACHE} .
                """
            }
            post {
                always {
                    echo '🧹 Cleaning Trivy cache for filesystem scan...'
                    sh "rm -rf ${TRIVY_CACHE}"
                }
            }
        }

        stage('Docker Image Scan') {
            steps {
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    echo '🔍 Scanning Docker images for vulnerabilities and generating HTML reports...'
                    sh """
                        mkdir -p ${TRIVY_CACHE}

                        echo "Scanning backend image..."
                        trivy image --exit-code 1 --severity CRITICAL \
                            --format template --template "@/contrib/html.tpl" \
                            -o trivy-backend-report.html ${BACKEND_IMAGE} || true

                        echo "Scanning frontend image..."
                        trivy image --exit-code 1 --severity CRITICAL \
                            --format template --template "@/contrib/html.tpl" \
                            -o trivy-frontend-report.html ${FRONTEND_IMAGE} || true
                    """
                }
            }
            post {
                always {
                    echo '🧹 Cleaning Trivy cache for Docker scan...'
                    sh "rm -rf ${TRIVY_CACHE}"

                    echo '📄 Publishing Trivy HTML reports...'
                    publishHTML([
                        reportDir: "${WORKSPACE}", 
                        reportFiles: 'trivy-backend-report.html', 
                        reportName: 'Backend Trivy Scan Report',
                        allowMissing: true, 
                        keepAll: true
                    ])
                    publishHTML([
                        reportDir: "${WORKSPACE}", 
                        reportFiles: 'trivy-frontend-report.html', 
                        reportName: 'Frontend Trivy Scan Report',
                        allowMissing: true, 
                        keepAll: true
                    ])

                    echo '💾 Saving Trivy reports to GitHub using SSH...'
                    sshagent(['github-ssh']) {
                        sh """
                            git config user.email "jenkins@example.com"
                            git config user.name "Jenkins CI"
                            git add trivy-backend-report.html trivy-frontend-report.html
                            git commit -m "Add latest Trivy scan reports [ci skip]" || true
                            git push origin ${BRANCH} || true
                        """
                    }
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline finished.'
        }
        failure {
            echo '❌ Pipeline failed due to build or security scan errors.'
            echo '🧹 Cleaning workspace after failure...'
            deleteDir()
        }
        success {
            echo '✅ Pipeline completed successfully.'
            echo '🧹 Cleaning workspace after success...'
            deleteDir()
        }
    }
}
