-- TODO This script is ready for you to create you tables for MS SQL Server
--  if the database does not yet exist you will need to add
--  create database edgetech to the script as well

USE edgetech;

DROP TABLE auth_user_role;
DROP TABLE auth_role;
DROP TABLE auth_user;
DROP TABLE customer;
DROP TABLE product;

CREATE TABLE auth_role (
                           auth_role_id int NOT NULL IDENTITY(1,1),
                           role_desc varchar(255) NULL,
                           role_name varchar(255) NULL,
                           PRIMARY KEY (auth_role_id)
);

CREATE TABLE auth_user (
                           auth_user_id int NOT NULL IDENTITY(1,1),
                           email varchar(255) NOT NULL,
                           first_name varchar(255) NOT NULL,
                           last_name varchar(255) NOT NULL,
                           password varchar(255) NOT NULL,
                           status varchar(255) NULL,
                           PRIMARY KEY (auth_user_id)
);

CREATE TABLE auth_user_role (
                                auth_user_id int NOT NULL,
                                auth_role_id int NOT NULL,
                                PRIMARY KEY (auth_user_id,auth_role_id),
                                CONSTRAINT FK_auth_user FOREIGN KEY (auth_user_id) REFERENCES auth_user (auth_user_id),
                                CONSTRAINT FK_auth_user_role FOREIGN KEY (auth_role_id) REFERENCES auth_role (auth_role_id)
);

CREATE TABLE customer (
                          id int NOT NULL IDENTITY(1,1),
                          address varchar(255) NULL,
                          name varchar(255) NULL,
                          phone_num varchar(255) NULL,
                          version int NULL,
                          PRIMARY KEY (id)
);

CREATE TABLE product (
                         id int NOT NULL IDENTITY(1,1),
                         category varchar(255) NULL,
                         description varchar(255) NULL,
                         image_url varchar(255) NULL,
                         price numeric(19,2) NULL,
                         product_id varchar(255) NULL,
                         version int NULL,
                         PRIMARY KEY (id)
);

INSERT INTO auth_role (role_name, role_desc) VALUES ('SUPER_USER','This user has ultimate rights for everything');
INSERT INTO auth_role (role_name, role_desc) VALUES ('ADMIN_USER','This user has admin rights for administrative work');
INSERT INTO auth_role (role_name, role_desc) VALUES ('SITE_USER','This user has access to site, after login - normal user');

insert into auth_user (first_name,last_name,email,password,status) values ('Ankit','Wasankar','admin@gmail.com','$2a$10$DD/FQ0hTIprg3fGarZl1reK1f7tzgM4RuFKjAKyun0Si60w6g3v5i','VERIFIED');

insert into auth_user_role (auth_user_id, auth_role_id) values ('1','1');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','2');
insert into auth_user_role (auth_user_id, auth_role_id) values ('1','3');

INSERT INTO customer (address, name, phone_num) VALUES ('8701 Bedford-Euless Rd','Edge Tech Academy','682-334-1111');
INSERT INTO customer (address, name, phone_num) VALUES ('1 First Command Rd','First Command','682-334-5679');
INSERT INTO customer (address, name, phone_num) VALUES ('8701 Bedford-Euless Rd','Ancora Education','682-334-5679');
INSERT INTO customer (address, name, phone_num) VALUES ('1 First Command Rd','American Airlines','682-334-5679');
INSERT INTO customer (address, name, phone_num) VALUES ('Scottsdale AZ','Homebid.com','801-225-2030');

INSERT INTO product(category, description, image_url, price, product_id) VALUES ('11','Edge Tech Agile','/images/AmericanEclipseHDR_Lefaudeux_3474.jpg',34.74,'112358132134');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('0','Edge Tech C#','/images/csharp.png',4.41,'6.022140857×10^23');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('3','Edge Tech CSS','/images/css.png',48.34,'8.314459848');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('2','Edge Tech HTML5','/images/html.png',37.72,'1.6021766×10^−19');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('1','Edge Tech Java 10','/images/java.png',9.87,'137.036');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('0','Edge Tech JavaScript','/images/javascript.png',2.98,'9.46073047 x 10^12');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('1','Edge Tech MongoDB','/images/mongodb.png',3.38,'2.718281828459045');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('0','Edge Tech MySQL','/images/mysql.png',11.99,'299792458');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('1','Edge Tech Spring Boot','/images/spring.png',12.72,'13.799±0.021*10^9');
INSERT INTO product(category, description, image_url, price, product_id) VALUES ('3','Edge Tech SQL','/images/sql.png',35.47,'384400');
