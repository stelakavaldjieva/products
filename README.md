# Задача: "Продукти", Екип 4, самостоятелна задача на Стела Кавалджиева
Целта на тази задача е;
1. Да създадем Spring Boot базирана система, 
до която се осъществява връзка с REST услуги.
2. Да осъществим връзка с Oracle DB Server чрез JDBC.
3. Да имплементираме логика:

3.1. Създаване на продукти

3.2. Търсене на продукти

3.3. Преглед на продукти

3.4. Преглед на информация за продукт

3.5. Продаване на продукти

3.6. Справка за продажби.

## Преди стартиране
В Oracle SQL Developer с datasource конфигурация от application.yml изпълнете всички команди от import_oracle.sql

## Стартиране
За компилиране на проекта се нуждаете от maven.

В папката на проекта изпълнете командата:

mvn spring-boot:run

## Документация
За информация относно използваните библиотеки и инструменти:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.6.RELEASE/maven-plugin/)
* [Spring Data JDBC](https://docs.spring.io/spring-data/jdbc/docs/current/reference/html/#reference)