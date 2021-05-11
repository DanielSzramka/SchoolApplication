CREATE TABLE `headmasters` (
  `headmaster_id` bigint unsigned NOT NULL AUTO_INCREMENT,
  `person_id` bigint unsigned NOT NULL,
  `user_id` bigint unsigned NOT NULL,
  `school_id` bigint unsigned NOT NULL,
  `start_date` varchar(45) COLLATE utf8_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`headmaster_id`),
  KEY `fk_headmaster_1_idx` (`person_id`),
  CONSTRAINT `fk_headmaster_1` FOREIGN KEY (`person_id`) REFERENCES `persons` (`person_id`),
  KEY `fk_headmaster_2_idx` (`user_id`),
  CONSTRAINT `fk_headmaster_2` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`),
   KEY `fk_headmaster_3_idx` (`school_id`),
  CONSTRAINT `fk_headmaster_3` FOREIGN KEY (`school_id`) REFERENCES `schools` (`school_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;