pipeline {
    agent {
        docker {
            image 'maven:3.9.8-eclipse-temurin-21'  // Maven + Java
            args '-u root --shm-size=1g'  // Запуск от root
        }
    }

    environment {
        BROWSER = 'chrome'
        SELENIDE_HEADLESS = 'true'
    }

    stages {

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
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh '''
                    mvn test \
                        -Dselenide.browser=chrome \
                        -Dselenide.headless=true \
                        -Dchromeoptions.args="--headless,--disable-gpu,--no-sandbox,--disable-dev-shm-usage,--window-size=1920,1080,--remote-allow-origins=*"
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