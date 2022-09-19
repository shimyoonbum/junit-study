drop table if exists `book`;
CREATE TABLE `book` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `author` varchar(20) NOT NULL,
  `title` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
)