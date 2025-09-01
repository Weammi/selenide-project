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
                        set -e  # Выход при любой ошибке

                        echo "=== Installing Chrome ==="
                        apt-get update
                        apt-get install -y wget unzip

                        # Установка Chrome
                        if [ ! -f /usr/bin/google-chrome-stable ]; then
                            wget -q -O /tmp/chrome.deb "https://dl.google.com/linux/direct/google-chrome-stable_current_amd64.deb"
                            apt-get install -y /tmp/chrome.deb
                            rm /tmp/chrome.deb
                        fi

                        # Получение версий
                        CHROME_VERSION=$(google-chrome-stable --version | awk '{print $3}')
                        CHROME_MAJOR=$(echo $CHROME_VERSION | cut -d'.' -f1)
                        echo "Chrome: $CHROME_VERSION, Major: $CHROME_MAJOR"

                        # Установка ChromeDriver
                        echo "Downloading ChromeDriver for major version $CHROME_MAJOR"
                        wget -q -O /tmp/chromedriver_version "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_MAJOR"
                        CHROMEDRIVER_VERSION=$(cat /tmp/chromedriver_version)
                        echo "ChromeDriver version: $CHROMEDRIVER_VERSION"

                        wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROMEDRIVER_VERSION/chromedriver_linux64.zip"
                        unzip /tmp/chromedriver.zip -d /usr/local/bin/
                        chmod +x /usr/local/bin/chromedriver

                        # Проверка
                        echo "Installation completed:"
                        google-chrome-stable --version
                        chromedriver --version
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