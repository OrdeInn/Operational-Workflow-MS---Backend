CREATE TABLE `orders` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `customer_id` bigint NOT NULL,
    `total_cost` double NOT NULL,
    `creation_date` datetime(6) DEFAULT NULL,
    `completion_date` datetime(6) DEFAULT NULL,
    `status` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`customer_id`) REFERENCES `customers` (`id`)
);

CREATE TABLE `order_basket` (
    `order_id` bigint NOT NULL,
    `basket`int,
    `basket_key` bigint NOT NULL,
    PRIMARY KEY (`order_id`,`basket_key`)
);
