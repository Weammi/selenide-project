#!/bin/bash

# Переменные
PROJECT_DIR="/path/to/your/project"
LOG_FILE="$PROJECT_DIR/cron.log"
DATE=$(date +"%Y-%m-%d %H:%M:%S")

echo "$DATE - Starting test execution" >> $LOG_FILE

# Переходим в директорию проекта
cd $PROJECT_DIR

# Запускаем тесты через Docker
docker-compose build --no-cache
docker-compose up --abort-on-container-exit

# Проверяем результат выполнения
if [ $? -eq 0 ]; then
    echo "$DATE - Tests completed successfully" >> $LOG_FILE
else
    echo "$DATE - Tests failed" >> $LOG_FILE
    exit 1
fi