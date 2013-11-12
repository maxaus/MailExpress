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
  `path` varchar(255) NOT NULL,
  `message_id` bigint(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_attachment_message` (`message_id`) USING BTREE,
  CONSTRAINT `fk_attachment_message` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `contact` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(120) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `folder`
--

DROP TABLE IF EXISTS `folder`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `folder` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `icon_path` varchar(255) DEFAULT NULL,
  `folder_name` varchar(255) NOT NULL,
  `parent_folder_id` bigint(20) DEFAULT NULL,
  `system_folder` bit(1) NOT NULL DEFAULT b'0',
  PRIMARY KEY (`id`),
  KEY `fk_folder_folder` (`parent_folder_id`) USING BTREE,
  CONSTRAINT `fk_folder_folder` FOREIGN KEY (`parent_folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `message` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `body` longtext,
  `sent_date` datetime DEFAULT NULL,
  `subject` varchar(100) DEFAULT NULL,
  `unread` bit(1) NOT NULL DEFAULT b'1',
  `folder_id` bigint(20) NOT NULL,
  `sender_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_folder` (`folder_id`) USING BTREE,
  KEY `fk_message_contact` (`sender_id`) USING BTREE,
  CONSTRAINT `fk_message_contact` FOREIGN KEY (`sender_id`) REFERENCES `contact` (`id`),
  CONSTRAINT `fk_message_folder` FOREIGN KEY (`folder_id`) REFERENCES `folder` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `message_contact`
--

DROP TABLE IF EXISTS `message_contact`;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
CREATE TABLE `message_contact` (
  `message_id` bigint(20) NOT NULL,
  `receivers_id` bigint(20) NOT NULL,
  UNIQUE KEY `uk_message_contact_contact` (`receivers_id`) USING BTREE,
  KEY `fk_message_contact_contact` (`receivers_id`) USING BTREE,
  KEY `fk_message_contact_message` (`message_id`) USING BTREE,
  CONSTRAINT `fk_message_contact_message` FOREIGN KEY (`message_id`) REFERENCES `message` (`id`),
  CONSTRAINT `fk_message_contact_contact` FOREIGN KEY (`receivers_id`) REFERENCES `contact` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET character_set_client = @saved_cs_client;








