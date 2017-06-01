CREATE DATABASE  IF NOT EXISTS `my` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `my`;
-- MySQL dump 10.13  Distrib 5.7.12, for Win64 (x86_64)
--
-- Host: localhost    Database: my
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `base_user`
--

DROP TABLE IF EXISTS `base_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `base_user` (
  `id` int(16) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(64) DEFAULT NULL COMMENT '用户名称',
  `type` tinyint(4) DEFAULT NULL COMMENT '用户类型',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='基础用户';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `base_user`
--

LOCK TABLES `base_user` WRITE;
/*!40000 ALTER TABLE `base_user` DISABLE KEYS */;
INSERT INTO `base_user` VALUES (1,'admin',1,1);
/*!40000 ALTER TABLE `base_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource`
--

DROP TABLE IF EXISTS `resource`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource` (
  `id` bigint(16) NOT NULL AUTO_INCREMENT COMMENT '资源ID',
  `pid` bigint(16) DEFAULT NULL COMMENT '父ID',
  `type` tinyint(4) DEFAULT NULL COMMENT '类型：1、菜单；2、按钮',
  `name` varchar(64) DEFAULT NULL COMMENT '名称',
  `url` varchar(100) DEFAULT NULL COMMENT '地址',
  `icon` varchar(64) DEFAULT NULL COMMENT '图标',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `sort` tinyint(4) DEFAULT NULL COMMENT '排序',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8 COMMENT='URL资源表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource`
--

LOCK TABLES `resource` WRITE;
/*!40000 ALTER TABLE `resource` DISABLE KEYS */;
INSERT INTO `resource` VALUES (1,0,1,'模板',NULL,'icon-file-text',1,1),(2,1,1,'datagrid','/common/toDatagrid','',1,1),(3,0,1,'系统设置',NULL,'icon-cog',1,1),(4,3,1,'资源设置','/resource/toList',NULL,1,1);
/*!40000 ALTER TABLE `resource` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `resource_user`
--

DROP TABLE IF EXISTS `resource_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `resource_user` (
  `user_id` bigint(16) NOT NULL COMMENT '用户ID',
  `resource_id` bigint(16) NOT NULL COMMENT '资源ID',
  PRIMARY KEY (`user_id`,`resource_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `resource_user`
--

LOCK TABLES `resource_user` WRITE;
/*!40000 ALTER TABLE `resource_user` DISABLE KEYS */;
INSERT INTO `resource_user` VALUES (1,1),(1,2),(1,3),(1,4);
/*!40000 ALTER TABLE `resource_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_login`
--

DROP TABLE IF EXISTS `user_login`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_login` (
  `user_id` bigint(16) NOT NULL COMMENT '用户ID',
  `loginname` varchar(64) NOT NULL COMMENT '登录账号',
  `password` varchar(64) NOT NULL COMMENT '登录密码',
  `status` tinyint(4) DEFAULT NULL COMMENT '状态',
  `login_time` datetime DEFAULT NULL COMMENT '最后登录时间',
  `login_ip` varchar(64) DEFAULT NULL COMMENT '最后登录IP',
  PRIMARY KEY (`loginname`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='登录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_login`
--

LOCK TABLES `user_login` WRITE;
/*!40000 ALTER TABLE `user_login` DISABLE KEYS */;
INSERT INTO `user_login` VALUES (1,'admin','admin',1,NULL,NULL);
/*!40000 ALTER TABLE `user_login` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-13 22:21:45
