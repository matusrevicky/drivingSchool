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
INSERT INTO `availabletime` VALUES (1,'2018-12-11 01:00:00','2018-12-11 05:55:00',3,'BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:New\r\nCATEGORIES:group00\r\nDTSTART;TZID=Europe/Prague:20181211T010000\r\nDTEND;TZID=Europe/Prague:20181211T055500\r\nCREATED:20181211T193720Z\r\nDTSTAMP:20181211T193720Z\r\nUID:20181211T203720-7jfxtras.org\r\nEND:VEVENT');
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
INSERT INTO `reservation` VALUES ('BEGIN:VEVENT\r\nORGANIZER:mailto:default_organizer@example.org\r\nSUMMARY:New\r\nCATEGORIES:group00\r\nDTSTART;TZID=Europe/Prague:20181213T021000\r\nDTEND;TZID=Europe/Prague:20181213T070500\r\nCREATED:20181211T193720Z\r\nDTSTAMP:20181211T202831Z\r\nUID:20181211T203720-7jfxtras.org\r\nSEQUENCE:2\r\nEND:VEVENT',0,3,2);
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
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'u','p','p','p2s@s.sk','$2a$10$Yd/ysay5w2VrPAqcBjiFeuTWnv8F5su9t.YouyZ3I7CsjdkVNsRQC','p','2018-12-08 20:49:58','2018-12-10 21:31:12','2018-12-10 10:23:09',1,0,'admin'),(2,'Matus','Revicky','s','s@sk.sk','$2a$10$f0szuWmoHbFp5Fp1gEvlf.WzNLYqcnp5XJcbj/oIuaEJF6JqQGtNi','s','2018-12-08 20:58:24','2018-12-11 14:00:20','2018-12-10 10:10:34',1,0,'student'),(3,'Instruktor','to','t','t@sd.sjk','$2a$10$IELGS7m183jirbdmydqyb.R9SkGhVffnIshFpTPpD7spCSFPc5WKW','tt','2018-12-08 20:59:05','2018-12-11 20:57:02','2018-12-11 20:56:53',1,0,'teacher'),(6,'b','b','b','b@b.sk','$2a$10$1KVm0DDy1KJtI5Twqe2gtOp7i.zZ4sm2UbqlQZH4w301xaB38w.ve','b','2018-12-09 17:56:35','2018-12-10 10:15:54','2018-12-10 10:18:30',1,0,'admin'),(7,'g','g','g','g@g.sk','$2a$10$8Ax73czjAJHH4v6Xx92soOZ2gBOizmv.V.26VRcbYdSE3S3cxqZQy','g','2018-12-09 21:04:01','2018-12-09 21:07:53','2018-12-09 21:04:01',1,0,'teacher'),(8,'po','po','po','po@ak.sk','$2a$10$MfWwoM7S4RSt.H57PiukYeM.b3r7Ddyz2R6/kme38ymRUWb6rITO2','po','2018-12-10 08:55:12','2018-12-10 08:55:12','2018-12-10 08:55:12',1,0,'student'),(9,'Samo','Osw','a','aa@sd.sl','$2a$10$I6DOo7Y8NCoHMr41csSP.e3e6sEB89bvBp0m6hVi49hER0Y7XaBH6','','2018-12-10 09:02:29','2018-12-11 14:03:57','2018-12-10 09:02:29',1,0,'student'),(10,'l','l','l','l@lk.sk','$2a$10$dyhJHg6zR2SyuHC0y8u1CeB9qRjchMJ5YFV7q.tQl5V9/EtPwwoSW','l','2018-12-10 10:19:40','2018-12-10 10:24:24','2018-12-10 10:19:48',1,0,'student'),(11,'x','x','x','x@sk.sk','$2a$10$xqxsbg2Yhb5ffWl7wwdazeql/UJ1t6IOWMYDNezYO203uXKZG8kNO','x','2018-12-11 20:58:24','2018-12-11 21:48:51','2018-12-11 20:58:24',0,0,'student'),(12,'m','m','m','m@sk.sk','$2a$10$3OC77EAnC/B7jAEDOuOyNed7cLicXNdTla25Uf6t12btK.ZJ.20R.','m','2018-12-11 21:53:44','2018-12-11 21:53:44','2018-12-11 21:53:44',1,0,'student'),(13,'/','/','/','sasd.sl@sd.sk','$2a$10$H2mn0ycPtxxBs/CW/42xDe3HUuAAcLYsLxcuz8FQDICOhuNY1Ht52','/','2018-12-11 22:05:47','2018-12-11 22:05:47','2018-12-11 22:05:47',1,0,'student'),(14,'gy','gy','gy','gy@sf.kl','$2a$10$IQ4rmDzTkyBwxxdn791KWOoh5uAgcBslm5iJ2sGITNSsAeVnB2h32','gy','2018-12-11 22:07:27','2018-12-11 22:07:27','2018-12-11 22:07:27',1,0,'student');
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

-- Dump completed on 2018-12-11 22:19:18
