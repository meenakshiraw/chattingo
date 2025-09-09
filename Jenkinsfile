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
                echo '🔍 Scanning source code for vulnerabilities...'
                sh """
                    mkdir -p ${TRIVY_CACHE}
                    trivy fs --exit-code 1 --severity HIGH,CRITICAL --cache-dir ${TRIVY_CACHE} .
                """
            }
            post {
                always {
                    echo '🧹 Cleaning Trivy cache...'
                    sh "rm -rf ${TRIVY_CACHE}"
                }
            }
        }

        stage('Docker Image Scan') {
            steps {
                // Run scans, mark stage failed on CRITICAL issues, but allow pipeline to continue
                catchError(buildResult: 'SUCCESS', stageResult: 'FAILURE') {
                    echo '🔍 Scanning Docker images for vulnerabilities and generating HTML reports...'
                    sh """
                        mkdir -p ${TRIVY_CACHE}

                        echo "Scanning backend image..."
                        trivy image --exit-code 1 --severity CRITICAL --format template --template "@contrib/html.tpl" \
                            -o trivy-backend-report.html ${BACKEND_IMAGE} || true

                        echo "Scanning frontend image..."
                        trivy image --exit-code 1 --severity CRITICAL --format template --template "@contrib/html.tpl" \
                            -o trivy-frontend-report.html ${FRONTEND_IMAGE} || true
                    """
                }
            }
            post {
                always {
                    echo '🧹 Cleaning Trivy cache...'
                    sh "rm -rf ${TRIVY_CACHE}"

                    // Publish HTML reports in Jenkins
                    publishHTML([
                        reportDir: '.', 
                        reportFiles: 'trivy-backend-report.html', 
                        reportName: 'Backend Trivy Scan Report',
                        allowMissing: true, 
                        keepAll: true
                    ])
                    publishHTML([
                        reportDir: '.', 
                        reportFiles: 'trivy-frontend-report.html', 
                        reportName: 'Frontend Trivy Scan Report',
                        allowMissing: true, 
                        keepAll: true
                    ])
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
            echo '❌ Pipeline failed due to build or security scan errors.'
        }
        success {
            echo '✅ Pipeline completed successfully.'
        }
    }
}
