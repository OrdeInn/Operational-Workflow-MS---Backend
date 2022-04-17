ALTER TABLE factory ADD `warehouse_id` bigint NOT NULL;

CREATE TABLE `warehouse_stock` (
    `warehouse_id` bigint NOT NULL,
    `stock`int,
    `stock_key` bigint NOT NULL,
    PRIMARY KEY (`warehouse_id`,`stock_key`)
);

CREATE TABLE `supply_request` (
    `id` bigint NOT NULL AUTO_INCREMENT,
    `factory_id` bigint NOT NULL,
    `creation_date` datetime(6) DEFAULT NULL,
    `completion_date` datetime(6) DEFAULT NULL,
    `status` int DEFAULT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`factory_id`) REFERENCES `factory` (`id`)
);

CREATE TABLE `supply_request_request_basket` (
    `supply_request_id` bigint NOT NULL,
    `request_basket`int,
    `request_basket_key` bigint NOT NULL,
    PRIMARY KEY (`supply_request_id`,`request_basket_key`)
);