-- --------------------------------------------------------
-- Хост:                         127.0.0.1
-- Версия сервера:               8.0.23 - MySQL Community Server - GPL
-- Операционная система:         Win64
-- HeidiSQL Версия:              11.0.0.5919
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Дамп структуры базы данных gift
DROP DATABASE IF EXISTS `gift`;
CREATE DATABASE IF NOT EXISTS `gift` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `gift`;

-- Дамп структуры для таблица gift.authority
DROP TABLE IF EXISTS `authority`;
CREATE TABLE IF NOT EXISTS `authority` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.authority: ~12 rows (приблизительно)
/*!40000 ALTER TABLE `authority` DISABLE KEYS */;
REPLACE INTO `authority` (`id`, `name`) VALUES
	(2, 'ADD_CERTIFICATES'),
	(7, 'ADD_ORDERS'),
	(4, 'ADD_TAGS'),
	(10, 'DELETE_CERTIFICATES'),
	(12, 'DELETE_TAGS'),
	(1, 'READ_CERTIFICATES'),
	(3, 'READ_TAGS'),
	(6, 'READ_USER_ORDERS'),
	(5, 'READ_USERS'),
	(8, 'READ_WIDELY_USED_TAG'),
	(9, 'UPDATE_CERTIFICATES'),
	(11, 'UPDATE_TAGS');
/*!40000 ALTER TABLE `authority` ENABLE KEYS */;

-- Дамп структуры для таблица gift.certificate_tag
DROP TABLE IF EXISTS `certificate_tag`;
CREATE TABLE IF NOT EXISTS `certificate_tag` (
  `gift_certificate_id` bigint DEFAULT NULL,
  `tag_id` bigint DEFAULT NULL,
  KEY `FK_certificates_tags_gift_certificate` (`gift_certificate_id`),
  KEY `FK_certificates_tags_tag` (`tag_id`),
  CONSTRAINT `FK_certificates_tags_gift_certificate` FOREIGN KEY (`gift_certificate_id`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `FK_certificates_tags_tag` FOREIGN KEY (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.certificate_tag: ~22 rows (приблизительно)
/*!40000 ALTER TABLE `certificate_tag` DISABLE KEYS */;
REPLACE INTO `certificate_tag` (`gift_certificate_id`, `tag_id`) VALUES
	(1, 8),
	(1, 1),
	(3, 2),
	(5, 2),
	(5, 1),
	(5, 8),
	(6, 3),
	(12, 7),
	(7, 6),
	(15, 3),
	(15, 10),
	(16, 2),
	(19, 13),
	(19, 14),
	(20, 14),
	(20, 15),
	(24, 2),
	(24, 1),
	(25, 18),
	(25, 19),
	(30, 41),
	(30, 19);
/*!40000 ALTER TABLE `certificate_tag` ENABLE KEYS */;

-- Дамп структуры для таблица gift.gift_certificate
DROP TABLE IF EXISTS `gift_certificate`;
CREATE TABLE IF NOT EXISTS `gift_certificate` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `description` varchar(250) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `create_date` varchar(50) DEFAULT NULL,
  `last_update_date` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.gift_certificate: ~13 rows (приблизительно)
/*!40000 ALTER TABLE `gift_certificate` DISABLE KEYS */;
REPLACE INTO `gift_certificate` (`id`, `name`, `description`, `price`, `duration`, `create_date`, `last_update_date`) VALUES
	(1, 'Сауна Тритон', 'Сертификат на бесплатное посещение сауны Тритон на Маяковского, 16', 100.00, 60, '2020-12-16T14:48Z', '2020-12-16T14:49Z'),
	(3, 'Тату салон "Лисица"', 'Бесплатная татуировка 10x10 см', 125.00, 180, '2020-12-16T14:51Z', '2020-12-16T14:52:Z'),
	(5, 'SPA центр на Старовиленской, 15', 'Весь спектр SPA процедур', 125.00, 180, '2020-12-17T11:49Z', '2020-12-17T11:51Z'),
	(6, 'Programming courses \'Java Web development\'', 'Become good programmer for short period', 400.00, 90, '2020-12-17T12:00Z', '2020-12-17T12:01Z'),
	(7, 'Театр имени Янки Купалы', 'Посещение любого спектакля', 60.00, 14, '2020-12-18T07:11Z', '2020-12-18T10:05Z'),
	(12, 'SilverScreen', 'Просмотр любого кинофильма', 15.00, 45, '2020-12-18T09:22Z', '2020-12-18T09:25Z'),
	(15, 'Курсы английского языка', 'Курсы английского в online школе SkyEng', 150.00, 100, '2020-12-18T10:22Z', '2020-12-18T10:37Z'),
	(16, 'Тату салон "Imagine Dragon"', 'Бесплатная татуировка 12х12, + дизайн', 250.00, 90, '2020-12-21T12:21Z', '2020-12-21T12:21Z'),
	(19, 'Онлайн курсы C#', 'Бесплатный курс C# в школе программирования Litrex', 1222.00, 120, '2020-12-22T12:33Z', '2020-12-22T12:57Z'),
	(20, 'Курс Python Web development', 'Бесплатное прохождение курса веб разработки на Python', 900.00, 90, '2020-12-23T08:22Z', '2020-12-23T08:22Z'),
	(24, 'Pilates', 'Best pilates in whole Minsk', 225.00, 180, '2021-01-11T06:18Z', '2021-01-11T06:18Z'),
	(25, 'Circus', 'Circus visit for 2 people', 100.00, 120, '2021-01-11T06:20Z', '2021-01-11T06:20Z'),
	(30, 'Masterpiece gallery', 'Gallery visit for 2 people', 55.00, 365, '2021-01-12T08:44Z', '2021-01-12T08:44Z');
/*!40000 ALTER TABLE `gift_certificate` ENABLE KEYS */;

-- Дамп структуры для таблица gift.order
DROP TABLE IF EXISTS `order`;
CREATE TABLE IF NOT EXISTS `order` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `certificate_id` bigint NOT NULL,
  `order_date` varchar(50) DEFAULT NULL,
  `cost` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_order_gift_certificate` (`certificate_id`),
  KEY `FK_order_user` (`user_id`),
  CONSTRAINT `FK_order_gift_certificate` FOREIGN KEY (`certificate_id`) REFERENCES `gift_certificate` (`id`),
  CONSTRAINT `FK_order_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.order: ~16 rows (приблизительно)
/*!40000 ALTER TABLE `order` DISABLE KEYS */;
REPLACE INTO `order` (`id`, `user_id`, `certificate_id`, `order_date`, `cost`) VALUES
	(1, 1, 6, '2020-12-24T12:21Z', 250.00),
	(2, 6, 15, '2020-12-24T13:21Z', 400.00),
	(3, 3, 19, '2020-12-24T14:21Z', 999.00),
	(4, 4, 7, '2020-12-24T10:19Z', 60.00),
	(5, 3, 1, '2020-12-24T10:20Z', 100.00),
	(6, 3, 15, '2020-12-24T10:26Z', 150.00),
	(7, 3, 15, '2020-12-24T10:26Z', 150.00),
	(8, 5, 5, '2020-12-24T10:27Z', 125.00),
	(9, 5, 5, '2020-12-24T10:27Z', 125.00),
	(10, 2, 16, '2020-12-24T10:28Z', 250.00),
	(14, 1, 25, '2021-01-12T07:43Z', 100.00),
	(15, 1, 19, '2021-01-12T08:04Z', 222.00),
	(16, 1, 30, '2021-01-13T08:58Z', 55.00),
	(17, 1, 1, '2021-01-21T14:08:14.9825138', 100.00),
	(18, 1, 3, '2021-01-21T14:08:36.2681671', 125.00),
	(19, 1, 12, '2021-01-21T14:08:43.5779579', 15.00),
	(20, 1, 12, '2021-02-18T15:46:10.8327844', 15.00);
/*!40000 ALTER TABLE `order` ENABLE KEYS */;

-- Дамп структуры для таблица gift.role
DROP TABLE IF EXISTS `role`;
CREATE TABLE IF NOT EXISTS `role` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.role: ~2 rows (приблизительно)
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
REPLACE INTO `role` (`id`, `name`) VALUES
	(1, 'ADMIN'),
	(2, 'USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;

-- Дамп структуры для таблица gift.role_authority
DROP TABLE IF EXISTS `role_authority`;
CREATE TABLE IF NOT EXISTS `role_authority` (
  `role_id` bigint DEFAULT NULL,
  `authority_id` bigint DEFAULT NULL,
  KEY `FK_roles_authorities_authority` (`authority_id`),
  KEY `FK_roles_authorities_role` (`role_id`),
  CONSTRAINT `FK_roles_authorities_authority` FOREIGN KEY (`authority_id`) REFERENCES `authority` (`id`),
  CONSTRAINT `FK_roles_authorities_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.role_authority: ~15 rows (приблизительно)
/*!40000 ALTER TABLE `role_authority` DISABLE KEYS */;
REPLACE INTO `role_authority` (`role_id`, `authority_id`) VALUES
	(1, 1),
	(1, 3),
	(1, 2),
	(1, 4),
	(1, 5),
	(1, 6),
	(2, 1),
	(2, 3),
	(2, 5),
	(2, 6),
	(2, 7),
	(1, 10),
	(1, 12),
	(1, 9),
	(1, 11);
/*!40000 ALTER TABLE `role_authority` ENABLE KEYS */;

-- Дамп структуры для таблица gift.tag
DROP TABLE IF EXISTS `tag`;
CREATE TABLE IF NOT EXISTS `tag` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `operation` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `operation_date` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.tag: ~16 rows (приблизительно)
/*!40000 ALTER TABLE `tag` DISABLE KEYS */;
REPLACE INTO `tag` (`id`, `name`, `operation`, `operation_date`) VALUES
	(1, 'Активность', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(2, 'Красота', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(3, 'Образование', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(6, 'Театр', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(7, 'Кино', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(8, 'Отдых', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(10, 'Искусство', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(13, 'Programming', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(14, 'IT', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(15, 'Программирование', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(18, 'Circus', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(19, 'Развлечения', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(20, 'Museum', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(25, 'Sladkii Bubaleh', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(41, 'trololo', 'INSERT', '2021-02-04T11:47:51.8445638'),
	(43, 'Drotiki', 'INSERT', '2021-02-04T11:47:51.8445638');
/*!40000 ALTER TABLE `tag` ENABLE KEYS */;

-- Дамп структуры для таблица gift.user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL,
  `user_name` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `role_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_name` (`user_name`),
  KEY `FK_user_role` (`role_id`),
  CONSTRAINT `FK_user_role` FOREIGN KEY (`role_id`) REFERENCES `role` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Дамп данных таблицы gift.user: ~6 rows (приблизительно)
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
REPLACE INTO `user` (`id`, `name`, `user_name`, `password`, `role_id`) VALUES
	(1, 'Admin Adminov', 'admin', '$2a$12$Bgki.asLivdQuEaY3wGbnuwu8PdyOm1GVdTihnS5oXB1TQsTtpCqa', 1),
	(2, 'Petr Petrov', 'petr', '$2a$12$/5rcwYbuAq7Ot.NSfFNrnOEb8t5KVJXCgwW7TCDtLgDvKPd5PmezO', 2),
	(3, 'Alexey Alexeev', 'valerii', '$2a$12$h7nU6.6Oe8F5yXryEBtoeeWOKhyJJEG4dm.rYdHA0VOc023z79OVO', 2),
	(4, 'Mihail Mihailov', 'mihail', '$2a$12$LoWiGszjJjlsv2jPxtsz3e6scAiwmhqhe2dFcRuhre2WM7.QOSo1e', 2),
	(5, 'Artem Artemov', 'artem', '$2a$12$uH/ANs2pIXwT2z9mkiDQBe3pPnMFxLkzIKj/HJ/EKZdnfNthaW/UC', 2),
	(6, 'Dukalis Muhomorov', 'dukalis', '$2a$12$wz5hDd8JgEz.3WKdyvD7EeiSUZWozmnC/YU00HFY6X5HmhL7nU0Km', 2);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
