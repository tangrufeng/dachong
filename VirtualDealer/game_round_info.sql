/*
Navicat MySQL Data Transfer

Source Server         : dachong
Source Server Version : 50151
Source Host           : 192.168.0.106:3306
Source Database       : platform

Target Server Type    : MYSQL
Target Server Version : 50151
File Encoding         : 65001

Date: 2016-01-10 18:41:58
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `game_round_info`
-- ----------------------------
DROP TABLE IF EXISTS `game_round_info`;
CREATE TABLE `game_round_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键 ，自增',
  `result` int(11) NOT NULL COMMENT '牌局结果 0--和 1--庄赢 2--闲赢',
  `createTime` datetime NOT NULL,
  `gameType` varchar(3) NOT NULL COMMENT '游戏类型',
  `videoPath` varchar(100) NOT NULL COMMENT '视频地址',
  `cardInfo` varchar(1000) NOT NULL COMMENT '牌局信息',
  `dealer` int(11) NOT NULL COMMENT '荷官',
  `groupId` int(11) NOT NULL COMMENT '所属组',
  `lastPlayTime` datetime NOT NULL COMMENT '上次播放时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of game_round_info
-- ----------------------------
INSERT INTO `game_round_info` VALUES ('1', '0', '2015-12-27 16:55:46', 'BJL', '...', '[{\'timeline\':\'00:06\',\'event\':\'begin\',\'bcolor\':\'1\',\'bnum\':\'4\',\'fcolor\':\'3\',\'fnum\':\'8\'},{\'timeline\':\'00:11\',\'event\':\'deal\',\'bcolor\':\'1\',\'bnum\':\'2\',\'fcolor\':\'2\',\'fnum\':\'3\'},{\'timeline\':\'00:18\',\'event\':\'deal\',\'bcolor\':\'1\',\'bnum\':\'4\',\'fcolor\':\'3\',\'fnum\':\'8\'},{\'timeline\':\'00:27\',\'event\':\'deal\',\'bcolor\':\'2\',\'bnum\':\'5\',\'fcolor\':\'4\',\'fnum\':\'11\'},{\'timeline\':\'00:38\',\'event\':\'begin\',\'bcolor\':\'1\',\'bnum\':\'4\',\'fcolor\':\'3\',\'fnum\':\'8\'}]', '1', '1', '2015-12-27 16:55:46');
