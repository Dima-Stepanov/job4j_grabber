[![job4j_grabber](https://github.com/dmitryjob4j/job4j_grabber/actions/workflows/maven.yml/badge.svg)](https://github.com/dmitryjob4j/job4j_grabber/actions/workflows/maven.yml)

[![codecov](https://codecov.io/gh/dmitryjob4j/job4j_grabber/branch/master/graph/badge.svg?token=6W0J88JB0N)](https://codecov.io/gh/dmitryjob4j/job4j_grabber)

**Агрегатор Java Вакансий**

**Описание.**

Система запускается по расписанию. 
Период запуска указывается в настройках - app.properties.

Первый сайт будет sql.ru. В нем есть раздел job. Программа должна считывать все вакансии относящиеся к Java и записывать их в базу.
Доступ к интерфейсу будет через REST API.

**Расширение.**

1. В проект можно добавить новые сайты без изменения кода.
2. В проекте можно сделать параллельный парсинг сайтов.