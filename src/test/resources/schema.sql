
-- Дамп структуры для таблица gift.authority
CREATE TABLE IF NOT EXISTS `authority` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(100) NOT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.certificate_tag
CREATE TABLE IF NOT EXISTS `certificate_tag` (
	`gift_certificate_id` bigint DEFAULT NULL,
	`tag_id` bigint DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.gift_certificate
CREATE TABLE IF NOT EXISTS `gift_certificate` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`description` varchar(250) DEFAULT NULL,
	`price` decimal(10, 2) DEFAULT NULL,
	`duration` int DEFAULT NULL,
	`create_date` varchar(50) DEFAULT NULL,
	`last_update_date` varchar(50) DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.order
CREATE TABLE IF NOT EXISTS `order` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`user_id` bigint NOT NULL,
	`certificate_id` bigint NOT NULL,
	`order_date` varchar(50) DEFAULT NULL,
	`cost` decimal(10, 2) DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.role
CREATE TABLE IF NOT EXISTS `role` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.role_authority
CREATE TABLE IF NOT EXISTS `role_authority` (
	`role_id` bigint DEFAULT NULL,
	`authority_id` bigint DEFAULT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.tag
CREATE TABLE IF NOT EXISTS `tag` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`operation` varchar(10) NOT NULL,
	`operation_date` varchar(50) NOT NULL
);
-- Экспортируемые данные не выделены.
-- Дамп структуры для таблица gift.user
CREATE TABLE IF NOT EXISTS `user` (
	`id` bigint NOT NULL AUTO_INCREMENT,
	`name` varchar(50) NOT NULL,
	`username` varchar(50) NOT NULL,
	`password` varchar(150) NOT NULL,
	`role_id` bigint NOT NULL
);