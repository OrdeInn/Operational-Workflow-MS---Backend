CREATE TABLE `category` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `product` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `category_id` bigint NOT NULL,
    `price` double NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);

CREATE TABLE `supply` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `name` varchar(255) DEFAULT NULL,
    `category_id` bigint NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`category_id`) REFERENCES `category` (`id`)
);
