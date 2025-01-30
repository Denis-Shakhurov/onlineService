[![Maintainability](https://api.codeclimate.com/v1/badges/655a87f9d4549bb25fbb/maintainability)](https://codeclimate.com/github/Denis-Shakhurov/onlineService/maintainability)
[![Test Coverage](https://api.codeclimate.com/v1/badges/655a87f9d4549bb25fbb/test_coverage)](https://codeclimate.com/github/Denis-Shakhurov/onlineService/test_coverage)

---
# Проект написан с использованием [Javalin](javalin.io), [Hibernate](https://hibernate.org/), [Maven](https://maven.apache.org/), [PostgreSQL](https://www.postgresql.org/)

---
### Онлайн сервис для подбора услуг и онлайн записи на стрижку, маникюр и т.д. и т.п.

---
Перед установкой нужно установить соединение с БД PostgreSQL (пользователь "root", пароль "password") 
или изменить конфигурацию в файле application.properties

---
Установка:

mvn clean install

---
Запуск:

java -jar {path to file}/onlineService-1.0-SNAPSHOT.jar

---
После запуска:

прилжение доступно по адрусу: http://localhost:8080/
в приложении созданы пользователи для демонстрации проекта: smith@example.com, smithAlisa@example.com, denis@mail.com,
petrova@mail.com, best@mail.com - пароль для всех "password".