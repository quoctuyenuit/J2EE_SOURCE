DROP DATABASE IF EXISTS `j2ee`;
CREATE DATABASE `j2ee`;
USE `j2ee`;
DROP TABLE IF EXISTS `j2ee`.`transaction`;
DROP TABLE IF EXISTS `j2ee`.`order`;
DROP TABLE IF EXISTS `j2ee`.`user`;
DROP TABLE IF EXISTS `j2ee`.`user_type`;
DROP TABLE IF EXISTS `j2ee`.`image_sample`;
DROP TABLE IF EXISTS `j2ee`.`product`;
DROP TABLE IF EXISTS `j2ee`.`catalog`;
DROP TABLE IF EXISTS `j2ee`.`catalog_group`;
DROP TABLE IF EXISTS `j2ee`.`payment`;
DROP TABLE IF EXISTS `j2ee`.`taken_order`;

CREATE TABLE `user_type`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE `user`(
    `id` VARCHAR(255) NOT NULL PRIMARY KEY,
    `type_id` INT(11) NOT NULL,
    `name` NVARCHAR(50) NOT NULL,
    `email` CHAR(50) NOT NULL UNIQUE,
    `password` VARCHAR(255) NOT NULL,
    `address` NVARCHAR(255) DEFAULT NULL
);

ALTER TABLE `user`
ADD CONSTRAINT `FK_USER_USERTYPE`
FOREIGN KEY(`type_id`) REFERENCES `user_type`(`id`);

CREATE TABLE `image_sample`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` VARCHAR(255) NOT NULL,
    `product_id` INT(11)
);

CREATE TABLE `product`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` NVARCHAR(255) NOT NULL UNIQUE,
    `price` FLOAT,
    `discount` FLOAT DEFAULT 0
);

ALTER TABLE `image_sample`
ADD CONSTRAINT `FK_PRODUCT_IMAGE`
FOREIGN KEY (`product_id`) REFERENCES `product`(`id`);

CREATE TABLE `catalog`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `name` NVARCHAR(255),
    `parent_id` INT(11) DEFAULT NULL
);

ALTER TABLE `catalog`
ADD CONSTRAINT `FK_CATALOG_CATALOG`
FOREIGN KEY (`parent_id`) REFERENCES `catalog`(`id`);

CREATE TABLE `catalog_group`(
    `catalog_id` INT(11) NOT NULL,
    `product_id` INT(11) NOT NULL
);

ALTER TABLE `catalog_group`
ADD CONSTRAINT `PK_CATALOG_GROUP`
PRIMARY KEY (`catalog_id`, `product_id`);

CREATE TABLE `order`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `user_id` VARCHAR(255) NOT NULL,
    `created` DATE
);

ALTER TABLE `order`
ADD CONSTRAINT `FK_ORDER_USER`
FOREIGN KEY (`user_id`) REFERENCES `user`(`id`);

CREATE TABLE `transaction`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `status` INT(1) NOT NULL,
    `amount` FLOAT NOT NULL,
    `message` NVARCHAR(255),
    `created` DATE,
    `payment_id` INT(11) NOT NULL,
    `order_id` INT(11) NOT NULL
);

CREATE TABLE `payment`(
    `id` INT(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
    `payment_infor` NVARCHAR(255) NOT NULL
);

ALTER TABLE `transaction`
ADD CONSTRAINT `FK_TRANSACTION_PAYMENT`
FOREIGN KEY (`payment_id`) REFERENCES `payment`(`id`);

ALTER TABLE `transaction`
ADD CONSTRAINT `FK_TRANSACTION_ORDER`
FOREIGN KEY (`order_id`) REFERENCES `order`(`id`);

CREATE TABLE `taken_order`(
    `product_id` INT(11) NOT NULL,
    `order_id` INT(11) NOT NULL
);

ALTER TABLE `taken_order`
ADD CONSTRAINT `PK_TAKEN_ORDER`
PRIMARY KEY(`product_id`, `order_id`);

LOCK TABLE `user_type` WRITE;
DELETE FROM `user_type`;
INSERT INTO `j2ee`.`user_type`(`name`) VALUE('ADMIN');
INSERT INTO `j2ee`.`user_type`(`name`) VALUE('USER');
UNLOCK TABLES;
