USE `j2ee`;

SHOW VARIABLES LIKE 'local_infile';
SET GLOBAL `local_infile` = 1;

LOCK TABLE `j2ee`.`product` WRITE;
LOAD DATA LOCAL INFILE 'product.csv'
INTO TABLE `j2ee`.`product`
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(`name`, `price`, `discount`, `image_sample`);
UNLOCK TABLES;

LOCK TABLE `j2ee`.`image_sample` WRITE;
LOAD DATA LOCAL INFILE 'image_sample.csv'
INTO TABLE `j2ee`.`image_sample`
FIELDS TERMINATED BY ','
ENCLOSED BY '"'
LINES TERMINATED BY '\n'
IGNORE 1 ROWS
(`name`, `product_id`);
UNLOCK TABLES;

 /*select product.id, image_sample.name from product, image_sample, product_image where product.id = product_image.product_id and image_sample.id = product_image.image_id;*/
