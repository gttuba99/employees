CREATE DATABASE `employees` /*!40100 DEFAULT CHARACTER SET latin1 */;
CREATE TABLE `employees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `firstName` varchar(45) NOT NULL,
  `middleInitial` varchar(3) DEFAULT NULL,
  `lastName` varchar(45) NOT NULL,
  `dateOfBirth` date DEFAULT NULL,
  `dateOfEmployment` date NOT NULL,
  `status` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
