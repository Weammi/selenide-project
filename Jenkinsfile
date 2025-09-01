pipeline {
    agent {
        docker {
            image 'maven:3.9.8-eclipse-temurin-21'
            args '-u root -v /tmp/m2:/root/.m2'
        }
    }

    environment {
        BROWSER = 'chrome'
        SELENIDE_HEADLESS = 'true'
    }

    stages {
            stage('Install Chrome') {
                steps {
                    sh '''
                        # Установка зависимостей для Chrome
                        apt-get update
                        apt-get install -y wget gnupg libgbm-dev libnss3-dev libxss1 libasound2 libxtst6

                        # Установка Chrome
                        wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | gpg --dearmor -o /usr/share/keyrings/google-chrome.gpg
                        echo "deb [arch=amd64 signed-by=/usr/share/keyrings/google-chrome.gpg] http://dl.google.com/linux/chrome/deb/ stable main" > /etc/apt/sources.list.d/google-chrome.list
                        apt-get update
                        apt-get install -y google-chrome-stable

                        # Проверяем установку
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
                sh 'mvn test -Dselenide.headless=true -Dselenide.browser=chrome'
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