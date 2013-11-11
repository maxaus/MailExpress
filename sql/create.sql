-- MySQL dump 10.13  Distrib 5.1.26-rc, for Win32 (ia32)
--
-- Host: localhost    Database: mail
-- ------------------------------------------------------
-- Server version	5.1.26-rc-community

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `attachment`
--

DROP TABLE IF EXISTS `attachment`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `attachment` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `path` varchar(255) DEFAULT NULL,
  `message_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_o0nqmo5konpi2upduhtvm1fp2` (`message_id`),
  CONSTRAINT `FK_o0nqmo5konpi2upduhtvm1fp2` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `attachment`
--

LOCK TABLES `attachment` WRITE;
/*!40000 ALTER TABLE `attachment` DISABLE KEYS */;
/*!40000 ALTER TABLE `attachment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(120) NOT NULL,
  `in_contact_list` tinyint(1) DEFAULT NULL,
  `name` varchar(120) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (1,'mail@mail.ru',0,NULL);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `iconPath` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `parentFolderId` bigint(20) DEFAULT NULL,
  `systemFolder` tinyint(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_fm0q2m1q25obiykfrckjnd1mh` (`parentFolderId`),
  CONSTRAINT `FK_fm0q2m1q25obiykfrckjnd1mh` FOREIGN KEY (`parentFolderId`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `folder`
--

LOCK TABLES `folder` WRITE;
/*!40000 ALTER TABLE `folder` DISABLE KEYS */;
INSERT INTO `folder` VALUES
(1,'folder/folder.gif','Inbox',NULL,0),
(2,'folder/folder.gif','Drafts',NULL,0),
(3,'folder/folder.gif','Work',1,0),
(4,'folder/folder.gif','Info',1,0),
(5,'folder/folder.gif','Sent',NULL,0);
/*!40000 ALTER TABLE `folder` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `folder_id` bigint(20) NOT NULL,
  `date` datetime DEFAULT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  `unread` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_cnbmxcgr3k16cnbly6d863c6e` (`folder_id`),
  KEY `FK_k81nqbpioukiiytg4awpgbmeq` (`sender_id`),
  CONSTRAINT `FK_cnbmxcgr3k16cnbly6d863c6e` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`),
  CONSTRAINT `FK_k81nqbpioukiiytg4awpgbmeq` FOREIGN KEY (`sender_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (1,'fsd','sdfsdf',1,'2013-10-10 22:22:22',1,0),(2,'ghjghj','ggt',1,'2013-10-10 22:21:00',1,0),(3,'gdfgfd','tyjtyj',1,'2013-10-11 22:21:00',1,0),(4,'trgthjr','jtjjy',3,'2013-10-12 22:21:00',1,1),(5,'jyuk','tyty',3,'2013-10-10 22:21:00',1,0),(6,'kyu','ytuyt',3,'2013-10-10 22:21:00',1,1),(7,'kuyk','muu',1,'2013-10-14 22:21:00',1,1),(8,'ukyuk','ikkh',1,'2013-10-10 22:21:00',1,1),(9,'tyhy','lopl',1,'2013-10-10 22:21:00',1,1),(10,'ykyu','poiio',4,'2013-10-10 22:21:00',1,1),(11,'67j76','uytut',4,'2013-10-10 22:21:00',1,0),(12,'jjyuj','yuuu',1,'2013-10-10 22:21:00',1,0),(13,'rgrtg','dgdfgf',1,'2013-10-10 22:21:00',1,0),(14,'grtgr','fgdfg',1,'2013-10-10 22:21:00',1,0),(15,'ggrtgrt','dfgfg',1,'2013-10-10 22:21:00',1,1),(16,'grtgtrgtr','gfgdg',1,'2013-10-10 22:21:00',1,0);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message_contact`
--

DROP TABLE IF EXISTS `message_contact`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `message_contact` (
  `message_id` bigint(20) NOT NULL,
  `receivers_id` bigint(20) NOT NULL,
  UNIQUE KEY `UK_odou90cr84xgmwxu90ql30t1v` (`receivers_id`),
  KEY `FK_odou90cr84xgmwxu90ql30t1v` (`receivers_id`),
  KEY `FK_b8npefvuwgdljxde1xbotrjgx` (`message_id`),
  CONSTRAINT `FK_b8npefvuwgdljxde1xbotrjgx` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `FK_odou90cr84xgmwxu90ql30t1v` FOREIGN KEY (`receivers_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Dumping data for table `message_contact`
--

LOCK TABLES `message_contact` WRITE;
/*!40000 ALTER TABLE `message_contact` DISABLE KEYS */;
/*!40000 ALTER TABLE `message_contact` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2013-11-11 11:30:04
