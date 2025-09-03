pipeline {
    agent any  // Запускает на самой ноде (вашей VM)

    stages {
        stage('Setup Chrome') {
            steps {
                sh '''
                    echo "=== Installing Chrome ==="
                    # Добавление репозитория Google Chrome
                    wget -q -O - https://dl.google.com/linux/linux_signing_key.pub | sudo apt-key add -
                    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" | sudo tee /etc/apt/sources.list.d/google-chrome.list

                    # Установка Chrome и зависимостей
                    sudo apt-get update
                    sudo apt-get install -y google-chrome-stable

                    # Проверка установки
                    echo "Chrome version:"
                    google-chrome-stable --version
                '''
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
                    echo "Chrome version:"
                    google-chrome-stable --version
                '''
            }
        }

        stage('Build and Test') {
            steps {
                sh '''
                    # Компиляция и запуск тестов в одном этапе
                    mvn clean test -Dselenide.headless=true
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