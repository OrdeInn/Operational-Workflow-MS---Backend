CREATE TABLE `building` (
  `id` bigint NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `office` (
  `id` bigint NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

ALTER TABLE `users` ADD COLUMN `building_id` bigint;
ALTER TABLE `users` ADD CONSTRAINT `user_building_id` FOREIGN KEY (`building_id`) REFERENCES `building` (`id`);