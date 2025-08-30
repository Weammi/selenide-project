FROM maven:3.9.8-eclipse-temurin-21

WORKDIR /app

# Установка браузера и зависимостей
RUN apt-get update && \
    apt-get install -y \
    wget \
    gnupg \
    curl \
    unzip \
    && wget -q -O - https://dl-ssl.google.com/linux/linux_signing_key.pub | apt-key add - \
    && echo "deb [arch=amd64] http://dl.google.com/linux/chrome/deb/ stable main" >> /etc/apt/sources.list.d/google.list \
    && apt-get update \
    && apt-get install -y google-chrome-stable \
    && CHROME_VERSION=$(google-chrome-stable --version | awk '{print $3}') \
    && CHROME_DRIVER_VERSION=$(curl -s "https://chromedriver.storage.googleapis.com/LATEST_RELEASE_$CHROME_VERSION") \
    && wget -O /tmp/chromedriver.zip "https://chromedriver.storage.googleapis.com/$CHROME_DRIVER_VERSION/chromedriver_linux64.zip" \
    && unzip /tmp/chromedriver.zip -d /usr/local/bin/ \
    && chmod +x /usr/local/bin/chromedriver \
    && rm /tmp/chromedriver.zip \
    && rm -rf /var/lib/apt/lists/*

# Копирование проекта
COPY pom.xml .
COPY src ./src

# Настройка переменных окружения
ENV BROWSER=chrome
ENV SELENIDE_HEADLESS=true
ENV TZ=Europe/Moscow

# Запуск тестов
CMD ["mvn", "clean", "test", "-Dselenide.headless=true"]