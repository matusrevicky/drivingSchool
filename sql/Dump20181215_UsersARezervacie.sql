CREATE DATABASE  IF NOT EXISTS `mydb` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `mydb`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: mydb
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `availabletime`
--

DROP TABLE IF EXISTS `availabletime`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `availabletime` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `startTime` datetime DEFAULT NULL,
  `endTime` datetime DEFAULT NULL,
  `myUserId` int(11) NOT NULL,
  `eventString` varchar(500) DEFAULT NULL,
  `eventStringUID` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_AvailableTime_User_idx` (`myUserId`),
  CONSTRAINT `fk_AvailableTime_User` FOREIGN KEY (`myUserId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `availabletime`
--

LOCK TABLES `availabletime` WRITE;
/*!40000 ALTER TABLE `availabletime` DISABLE KEYS */;
INSERT INTO `availabletime` VALUES (1,'2018-12-10 01:40:00','2018-12-10 05:05:00',24,'BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Free Time \r\\nInst1\r\nCATEGORIES:group00\r\nDTSTART;TZID=Europe/Prague:20181210T014000\r\nDTEND;TZID=Europe/Prague:20181210T050500\r\nCREATED:20181215T135829Z\r\nDTSTAMP:20181215T135829Z\r\nUID:20181215T145829-0jfxtras.org\r\nEND:VEVENT','20181215T145829-0jfxtras.org');
/*!40000 ALTER TABLE `availabletime` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `reservation` (
  `eventString` varchar(500) DEFAULT NULL,
  `seenByStudent` tinyint(4) DEFAULT NULL,
  `instructorId` int(11) NOT NULL,
  `studentId` int(11) NOT NULL,
  UNIQUE KEY `eventString_UNIQUE` (`eventString`),
  KEY `fk_Reservation_User1_idx` (`instructorId`),
  KEY `fk_Reservation_User2_idx` (`studentId`),
  CONSTRAINT `fk_Reservation_User1` FOREIGN KEY (`instructorId`) REFERENCES `user` (`id`),
  CONSTRAINT `fk_Reservation_User2` FOREIGN KEY (`studentId`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES ('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 2 \\nStud 1 \\nNOT seen by student\r\nCATEGORIES:group23\r\nDTSTART;TZID=Europe/Prague:20181217T090000\r\nDTEND;TZID=Europe/Prague:20181217T111500\r\nCREATED:20181215T141414Z\r\nDTSTAMP:20181215T141539Z\r\nUID:20181215T151414-9jfxtras.org\r\nSEQUENCE:2\r\nEND:VEVENT',0,25,21),('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 1 \\nStud 1 \\nNOT seen by student\r\nCATEGORIES:group06\r\nDTSTART;TZID=Europe/Prague:20181220T080000\r\nDTEND;TZID=Europe/Prague:20181220T101500\r\nCREATED:20181215T140631Z\r\nDTSTAMP:20181215T140725Z\r\nUID:20181215T150631-5jfxtras.org\r\nSEQUENCE:1\r\nEND:VEVENT',0,24,21),('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 1 \\nStud 2 \\nNOT seen by student\r\nCATEGORIES:group21\r\nDTSTART;TZID=Europe/Prague:20181217T083000\r\nDTEND;TZID=Europe/Prague:20181217T104500\r\nCREATED:20181215T140835Z\r\nDTSTAMP:20181215T140843Z\r\nUID:20181215T150835-6jfxtras.org\r\nSEQUENCE:1\r\nEND:VEVENT',0,24,22),('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 1 \\nStud 3 \\nNOT seen by student\r\nCATEGORIES:group02\r\nDTSTART;TZID=Europe/Prague:20181220T103000\r\nDTEND;TZID=Europe/Prague:20181220T120000\r\nCREATED:20181215T141700Z\r\nDTSTAMP:20181215T141700Z\r\nUID:20181215T151700-10jfxtras.org\r\nEND:VEVENT',0,24,23),('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 1 \\nStud 1 \\nNOT seen by student\r\nCATEGORIES:group06\r\nDTSTART;TZID=Europe/Prague:20181217T070000\r\nDTEND;TZID=Europe/Prague:20181217T083000\r\nCREATED:20181215T140619Z\r\nDTSTAMP:20181215T140619Z\r\nUID:20181215T150619-4jfxtras.org\r\nEND:VEVENT',0,24,21),('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:Inst 1 \\nStud 2 \\nNOT seen by student\r\nCATEGORIES:group21\r\nDTSTART;TZID=Europe/Prague:20181219T160000\r\nDTEND;TZID=Europe/Prague:20181219T173000\r\nCREATED:20181215T140903Z\r\nDTSTAMP:20181215T140908Z\r\nUID:20181215T150903-7jfxtras.org\r\nSEQUENCE:2\r\nEND:VEVENT',0,24,22);
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fname` varchar(45) NOT NULL,
  `lname` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `password` varchar(60) NOT NULL,
  `phoneNumber` varchar(13) NOT NULL,
  `dateCreated` datetime DEFAULT NULL,
  `lastModified` datetime DEFAULT NULL,
  `lastLogin` datetime DEFAULT NULL,
  `active` tinyint(4) DEFAULT NULL,
  `ridesDone` int(11) DEFAULT NULL,
  `role` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','admin','admin@s.sk','$2a$10$fvjHz7uq5hKcerWMapwWG./Ik1NrCa7uzPloJ0T6JpvrnqawGoUFC','0090008','2018-12-08 20:49:58','2018-12-14 22:32:53','2018-12-14 22:32:48',1,0,'admin'),(21,'Stud','1','s1','s1@s.sk','$2a$10$7eP87Ln9K3WyMR1hHHo8UesbZE3qaQZtla..EYG30XNmdMgSoAivC','0987657835','2018-12-15 14:45:07','2018-12-15 14:45:07','2018-12-15 14:45:07',1,0,'student'),(22,'Stud','2','s2','s2@s.sk','$2a$10$83wpU5/i2FH8mVBW312Nj.BYMldtQQF4cZv7m9ejTXe4Jdwm5w0US','09877654532','2018-12-15 14:46:28','2018-12-15 14:46:38','2018-12-15 14:46:28',1,0,'student'),(23,'Stud','3','s3','s3@s.sk','$2a$10$SXWJhzIzsCDgQNlyD/sU4O.QJp5xXcYHaVRKtX8HJN.RRr.NtHpWy','09765345276','2018-12-15 14:47:34','2018-12-15 14:47:34','2018-12-15 14:47:34',1,0,'student'),(24,'Inst','1','i1','i1@s.sk','$2a$10$qluWcdoMXaGS3PBQXZsa.esxq21byuBUOH5xNjuzGaZFzxYANE.SC','0976538762','2018-12-15 14:48:27','2018-12-15 14:59:04','2018-12-15 14:48:27',1,0,'teacher'),(25,'Inst','2','i2','i2@s.sk','$2a$10$18yGVJJv5Ita0t7xN8SPIu19Lxqo3CnQ62lTKYkQXlItJXZ3cpJg6','0967754356','2018-12-15 14:49:03','2018-12-15 14:59:10','2018-12-15 14:49:03',1,0,'teacher');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-12-15 15:21:41
