pipeline {
    agent any

    stages {


        stage('Clean Workspace') {
            steps {
                echo 'Cleaning workspace...'
                deleteDir()  // This wipes the entire workspace
            }
        }

        stage('Git Clone') { 
            
            steps {
                echo "Cloning repository from GitHub..."
                git branch: 'meenakshi', url: 'https://github.com/meenakshiraw/chattingo.git',poll: false
            }
        }

        
        
    }
}
