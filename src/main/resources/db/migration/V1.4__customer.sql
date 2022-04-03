CREATE TABLE `customers` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `f_name` varchar(255) DEFAULT NULL,
    `l_name` varchar(255) DEFAULT NULL,
    `email` varchar(255) DEFAULT NULL,
    `address` varchar(255) DEFAULT NULL,
    `balance` double NOT NULL,
    PRIMARY KEY (`id`)
);