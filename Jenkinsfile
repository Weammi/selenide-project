pipeline {
    agent {
        docker {
            image 'maven:3.9.8-eclipse-temurin-21'
            args '-v $HOME/.m2:/root/.m2'
        }
    }

    triggers {
        cron('H */6 * * *')
    }

    environment {
        BROWSER = 'chrome'
        SELENIDE_HEADLESS = 'true'
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
                        echo "Git version:"
                        git --version
                    '''
                }
            }


    stages {
        stage('Setup Browser') {
            steps {
                sh '''
                    # Установка Chrome и ChromeDriver
                    apt-get update
                    apt-get install -y wget gnupg
                    wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add -
                    echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list
                    apt-get update
                    apt-get install -y google-chrome-stable

                    # Установка ChromeDriver
                    CHROME_VERSION=$(google-chrome --version | awk '{print $3}')
                    CHROME_DRIVER_VERSION=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION")
                    wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip"
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

        stage('Build') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('Test') {
            steps {
                sh '''
                    echo "=== STARTING TESTS ==="
                    cd /var/lib/jenkins/workspace/selenide-autotest
                    mvn clean test -Dselenide.headless=true -X
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