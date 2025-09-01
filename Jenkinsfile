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
            stage('Install Chrome') {
                steps {
                    sh '''
                        # Установка Chrome
                        apt-get update
                        apt-get install -y wget
                        wget -q -O /tmp/chrome.deb "https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"
                        apt-get install -y /tmp/chrome.deb
                        rm /tmp/chrome.deb

                        # Установка ChromeDriver
                        CHROME_VERSION=$(google-chrome-stable --version | awk '{print $3}')
                        wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROME_VERSION/chromedriver_linux64.zip"
                        unzip /tmp/chromedriver.zip -d /usr/local/bin/
                        chmod +x /usr/local/bin/chromedriver
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