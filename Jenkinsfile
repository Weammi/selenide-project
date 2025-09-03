pipeline {
    agent any  // Запускает на самой ноде (вашей VM)

    stages {
        stage('Setup Chrome') {
            steps {
                sh '''
                    # Установка Chrome на виртуальную машину
                    wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
                    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list
                    sudo apt-get update
                    sudo apt-get install -y google-chrome-stable
                '''
            }
        }

        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/Weammi/selenide-project.git'
            }
        }

        stage('Debug Info') {
            steps {
                sh '''
                    echo "=== DEBUG INFORMATION ==="
                    echo "Current directory: $(pwd)"
                    echo "Files in workspace:"
                    ls -la
                    echo "Maven version:"
                    mvn --version
                    echo "Java version:"
                    java --version
                '''
            }
        }

        stage('Build') {
            steps {
                sh 'mvn test -Dselenide.headless=true'
            }
        }

        stage('Test') {
            steps {
                sh '''
                    mvn test
                '''
            }
            post {
                always {
                    allure includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                }
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}