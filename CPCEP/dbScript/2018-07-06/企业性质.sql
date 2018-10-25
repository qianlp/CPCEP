/*
SQLyog Ultimate v10.00 Beta1
MySQL - 5.5.53 : Database - cpcep
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`cpcep` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `cpcep`;

/*Table structure for table `bs_nature_enterprise` */

DROP TABLE IF EXISTS `bs_nature_enterprise`;

CREATE TABLE `bs_nature_enterprise` (
  `uuid` varchar(64) NOT NULL,
  `remark` varchar(800) DEFAULT NULL COMMENT '备注',
  `wfStatus` varchar(1) DEFAULT NULL COMMENT '流程状态',
  `update_date` date DEFAULT NULL COMMENT '更新时间',
  `create_by` varchar(64) DEFAULT NULL COMMENT '创建人',
  `create_date` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT NULL COMMENT '更新人',
  `curUser` varchar(255) DEFAULT NULL,
  `del_flag` varchar(255) DEFAULT NULL COMMENT '标记',
  `docTypeId` varchar(255) DEFAULT NULL,
  `enterprise_name` varchar(64) DEFAULT NULL COMMENT '企业性质名称',
  PRIMARY KEY (`uuid`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

/*Data for the table `bs_nature_enterprise` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
