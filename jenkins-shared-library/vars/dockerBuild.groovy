def call() {
    echo "Building Docker images..."
    sh 'docker build -t backend-image:latest ./backend'
    sh 'docker build -t frontend-image:latest ./frontend'
}
