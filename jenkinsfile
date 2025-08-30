pipeline {
    agent any

    triggers {
        cron('H */6 * * *')
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

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn test -Dselenide.headless=true'
            }
            post {
                always {
                    allure includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
                }
            }
        }

        stage('Allure Report') {
            steps {
                allure includeProperties: false,
                    jdk: '',
                    results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            cleanWs()
        }
    }
}