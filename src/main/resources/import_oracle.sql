-- Create Oracle database tables and populate with example data
-- Drop existing table for cars
DROP TABLE CAR PURGE;

-- Create table for cars
CREATE TABLE CAR
(
    ID          NUMBER generated as identity,
    BRAND_NAME  VARCHAR2(40) not null,
    COLOR       VARCHAR2(40) not null,
    PRICE       DECIMAL,
    WEIGHT      NUMBER,
    LENGTH      NUMBER,
    QUANTITY    NUMBER
);

-- Insert some cars
INSERT INTO CAR (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES ('Mercedes', 'blue', 450, 10000, 2000, 10);

INSERT INTO CAR (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('BMW', 'blue', 450, 10000, 2000, 20);

INSERT INTO CAR (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('Ferrari', 'yellow', 550, 100000, 3500, 30);

-- Drop existing table for phones
DROP TABLE PHONE PURGE;

-- Create table for phones
CREATE TABLE PHONE
(
    ID          NUMBER generated as identity,
    BRAND_NAME  VARCHAR2(40) not null,
    COLOR       VARCHAR2(40) not null,
    PRICE       DECIMAL,
    WEIGHT      NUMBER,
    LENGTH      NUMBER,
    QUANTITY    NUMBER
);

-- Insert some phones
INSERT INTO PHONE (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('Nokia', 'blue', 5, 100, 1, 1000);

INSERT INTO PHONE (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('Samsung', 'white', 8, 400, 1, 2000);

INSERT INTO PHONE (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('iHunt', 'red', 6, 50, 1, 3000);

-- Drop existing table for tvs
DROP TABLE TV PURGE;

-- Create table for TV-s
CREATE TABLE TV
(
    ID          NUMBER generated as identity,
    BRAND_NAME  VARCHAR2(40) not null,
    COLOR       VARCHAR2(40) not null,
    PRICE       DECIMAL,
    WEIGHT      NUMBER,
    LENGTH      NUMBER,
    QUANTITY    NUMBER
);

-- Insert some TV-s
INSERT INTO TV (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('ViewStar', 'blue', 105, 150, 2, 100);

INSERT INTO TV (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('LG', 'black', 85, 1200, 2, 200);

INSERT INTO TV (BRAND_NAME, COLOR, LENGTH, PRICE, WEIGHT, QUANTITY)
VALUES  ('Samsung', 'red', 105, 180, 2, 300);

-- Drop existing table for sales
DROP TABLE SALE PURGE;

-- Create table for sales
CREATE TABLE SALE
(
    SALE_ID         NUMBER generated as identity,
    PRODUCT_ID      NUMBER,
    NB_SOLD         NUMBER,
    SALE_DATE       DATE,
    PRODUCT_TYPE    INT,
    PRICE           DECIMAL
);