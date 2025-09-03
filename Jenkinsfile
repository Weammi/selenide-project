pipeline {
    agent any

    // ОБЯЗАТЕЛЬНО добавляем эту секцию!
    tools {
        // 'jdk21' - это произвольное имя, которое вы дали при настройке JDK в Jenkins
        jdk 'jdk21'
        // Также убедитесь, что Maven настроен корректно
        maven 'maven'
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
                    mvn --version // Теперь здесь будет версия от нужного JDK!
                    echo "Java version:"
                    java --version
                    echo "Chrome version:"
                    google-chrome-stable --version
                '''
            }
        }

        stage('Build and Test') {
            steps {
                sh 'mvn clean test -Dselenide.headless=true'
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