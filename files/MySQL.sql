/*
SQLyog Ultimate v11.33 (64 bit)
MySQL - 5.6.21 : Database - ares_knowledge_base
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ares_knowledge_base` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `ares_knowledge_base`;

/*Table structure for table `t_analyze_ansj_ambiguity_sentence` */

DROP TABLE IF EXISTS `t_analyze_ansj_ambiguity_sentence`;

CREATE TABLE `t_analyze_ansj_ambiguity_sentence` (
  `id_pk` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sentence` varchar(256) NOT NULL COMMENT '歧义句',
  `state` int(11) DEFAULT '0' COMMENT '记录状态 0：有效 1：逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='歧义句表';

/*Table structure for table `t_analyze_ansj_ambiguity_word` */

DROP TABLE IF EXISTS `t_analyze_ansj_ambiguity_word`;

CREATE TABLE `t_analyze_ansj_ambiguity_word` (
  `id_pk` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `sentence_id` bigint(64) NOT NULL COMMENT '句子ID，外键sentence表ID',
  `word` varchar(64) NOT NULL COMMENT '词',
  `speech` varchar(32) DEFAULT NULL COMMENT '词性',
  `state` int(11) DEFAULT '0' COMMENT '记录状态 0：有效 1：逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8 COMMENT='歧义句分词表';

/*Table structure for table `t_analyze_ansj_segment` */

DROP TABLE IF EXISTS `t_analyze_ansj_segment`;

CREATE TABLE `t_analyze_ansj_segment` (
  `id_pk` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyword` varchar(256) NOT NULL COMMENT '关键词',
  `speech` varchar(16) NOT NULL COMMENT '词性',
  `word_frequency` int(11) NOT NULL COMMENT '词频',
  `state` int(11) DEFAULT '0' COMMENT '记录状态 0：有效 1：逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=4456381 DEFAULT CHARSET=utf8 COMMENT='分词词库';

/*Table structure for table `t_analyze_ansj_user_library` */

DROP TABLE IF EXISTS `t_analyze_ansj_user_library`;

CREATE TABLE `t_analyze_ansj_user_library` (
  `id_pk` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `keyword` varchar(256) NOT NULL COMMENT '关键词',
  `speech` varchar(16) NOT NULL COMMENT '词性',
  `word_frequency` int(11) NOT NULL COMMENT '词频',
  `state` int(11) DEFAULT '0' COMMENT '记录状态 0：有效 1：逻辑删除',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户自定义词库';

/*Table structure for table `t_sys_dict_config` */

DROP TABLE IF EXISTS `t_sys_dict_config`;

CREATE TABLE `t_sys_dict_config` (
  `id_pk` bigint(64) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
  `table_name` varchar(64) NOT NULL COMMENT '词库表表名',
  `table_desc` varchar(256) DEFAULT NULL COMMENT '表描述',
  `is_update` int(8) NOT NULL DEFAULT '0' COMMENT '词库是否更新 0：未更新 非0：已更新',
  `operator` varchar(512) DEFAULT NULL COMMENT '分析节点机器名，多个用逗号分隔',
  `node_number` int(8) NOT NULL DEFAULT '1' COMMENT '分析处理节点数',
  `state` int(8) DEFAULT '0' COMMENT '记录状态 0：有效 1：逻辑删除',
  `update_time` datetime DEFAULT NULL COMMENT '记录最后更新时间',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '记录创建时间',
  PRIMARY KEY (`id_pk`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8 COMMENT='词典配置表';

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
