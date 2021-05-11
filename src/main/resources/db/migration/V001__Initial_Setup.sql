CREATE TABLE `addresses` (
  `address_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `zip_code` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `city` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `street` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `apartment_number` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `flat_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `persons` (
  `person_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `address_id` bigint unsigned NOT NULL,
  `name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `second_name` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `surname` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `identity_number` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `citizenship` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `nationality` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`person_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

--CREATE TABLE `school_boys` (
--  `school_boy_id` bigint unsigned NOT NULL AUTO_INCREMENT,
--  `class_id` bigint unsigned DEFAULT NULL,
--  `date_of_birth` date NOT NULL,
--  `place_of_birth` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
--  `sex` tinytext COLLATE utf8_unicode_ci,
--  `status` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
--  PRIMARY KEY (`school_boy_id`)
--) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `users` (
  `user_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `mail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password_hash` char(60) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `parents` (
  `parent_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `person_id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL,
  `phone_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`parent_id`),
  KEY `fk_parents_1_idx` (`person_id`),
  CONSTRAINT `fk_parents_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`),
  KEY `fk_parents_2_idx` (`user_id`),
  CONSTRAINT `fk_parents_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `schools` (
  `school_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `address_id` bigint unsigned NOT NULL,
  `tax_number` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `full_name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `short_name` varchar(255) COLLATE utf8_unicode_ci DEFAULT NULL,
  `type` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  `phone_number` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  `mail` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`school_id`),
  KEY `fk_schools_1_idx` (`address_id`),
  CONSTRAINT `fk_schools_1` FOREIGN KEY (`address_id`) REFERENCES `addresses` (`address_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `classes` (
  `class_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `school_id` bigint unsigned NOT NULL,
  `class_name` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`class_id`),
  KEY `fk_classes_1_idx` (`school_id`),
  CONSTRAINT `fk_classes_1` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE `roles` (
  `role_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `user_id` bigint unsigned NOT NULL,
  `role` varchar(45) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`role_id`),
  KEY `fk_roles_1_idx` (`user_id`),
  CONSTRAINT `fk_roles_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
