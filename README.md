[![job4j_grabber](https://github.com/Dima-Stepanov/job4j_grabber/actions/workflows/maven.yml/badge.svg)](https://github.com/Dima-Stepanov/job4j_grabber/actions/workflows/maven.yml)

[![codecov](https://codecov.io/gh/Dima-Stepanov/job4j_grabber/branch/master/graph/badge.svg?token=6W0J88JB0N)](https://codecov.io/gh/Dima-Stepanov/job4j_grabber)

# Агрегатор Java Вакансий

## Описание

Система запускается по расписанию.
Период запуска указывается в настройках - app.properties.

Первый сайт будет [Хабр Карьера](https://career.habr.com/vacancies/java_developer). В нем есть раздел job. <br>
Программа должна считывать все вакансии относящиеся к Java и записывать их в базу.
Доступ к интерфейсу будет через браузер по адресу [http://localhost:9000/](http://localhost:9000).
При необходимости проект можно дополнить для работы с любым сайтом, <br>
Для этого достаточно реализовать парсер конкретного сайта реализовав интерфейс "Parse"

## Стек технологий

Java 16 <br>
Maven 3.6 <br>
org.jsoup <br>
PostgreSQL 14 <br>
H2 database 2.0 <br>
Liqubase 4.15 <br>
Sl4j + Log4j 5.6.11 <br>
org.quartz-schedule 2.3.2 <br>
Junit 4.12 <br>

## Требование к окружению

JDK 16, Maven 3.8, PostgreSQL 14.

## Запуск приложения

1. Создайте базу данных grabber при помощи консоли PostgreSQL или терминала pgAdmin:<br>
   """CREATE DATABASE grabber"""
2. Скопировать проект из репозитория по ссылке:
   <a href=https://git@github.com:Dima-Stepanov/job4j_grabber.git><b>Агрегатор Java Вакансий</b></a>
3. Перейдите в корень проекта и при помощи Maven соберите проект командой:<br>
   """mvn install -Pproduction -Dmaven.test.skip=true"""
4. После успешной сборки проекта перейдите в каталог собранного проекта <b>target</b> и запустите приложение
   командой:<br>
   """java -jar grabber.jar"""

## Взаимодействие с приложением

1. В файле app.properties доступны следующие настройки:
   > time=120 интервал опроса сайта в секундах
   > port=9000 номер порта на котором доступна веб страница с результатом.
2. Для просмотра результата работы парсера необходима перейти на страницу:
   > localhost:9000

![localhost9000.jpg](img%2Flocalhost9000.jpg) <br>

Рисунок 1. Отображение страницы с результатом.

### Контакты

> email: [haoos@inbox.ru](mailto:haoos@inbox.ru) <br>
> tl: [Dima_software](https://t.me/Dima_software) <br>
> github.com: [Dima-Stepanov](https://github.com/Dima-Stepanov)
