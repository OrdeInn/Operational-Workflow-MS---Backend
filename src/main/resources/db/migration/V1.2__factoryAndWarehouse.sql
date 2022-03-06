CREATE TABLE `factory` (
    `id` bigint NOT NULL,
    `location` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

CREATE TABLE `warehouse` (
    `id` bigint NOT NULL,
    `location` varchar(255) DEFAULT NULL,
    `name` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

ALTER TABLE `users`
DROP FOREIGN KEY `user_building_id`;