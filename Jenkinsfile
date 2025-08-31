pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',
                url: 'https://github.com/Weammi/selenide-project.git'
            }
        }

        stage('Test') {
            steps {
                sh '''
                    # Установка зависимостей
                    apt-get update
                    apt-get install -y wget

                    # Запуск тестов
                    mvn clean test -Dselenide.headless=true -Dselenide.browser=chrome
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
}