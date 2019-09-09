-- phpMyAdmin SQL Dump
-- version phpStudy 2014
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2019 年 09 月 07 日 18:56
-- 服务器版本: 5.5.47
-- PHP 版本: 5.3.29

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- 数据库: `eltyl_web`
--

-- --------------------------------------------------------

--
-- 表的结构 `persistent_logins`
--

CREATE TABLE IF NOT EXISTS `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`series`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `sys_permission`
--

CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL,
  `name` varchar(128) NOT NULL COMMENT '权限名称',
  `code` varchar(128) NOT NULL COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统权限表' AUTO_INCREMENT=6 ;

--
-- 转存表中的数据 `sys_permission`
--

INSERT INTO `sys_permission` (`id`, `pid`, `name`, `code`) VALUES
(1, 0, '管理商品', 'good:manage'),
(2, 1, '添加商品', 'good:add'),
(3, 1, '删除商品', 'good:del'),
(4, 1, '修改商品', 'good:update'),
(5, 1, '查看商品', 'good:view');

-- --------------------------------------------------------

--
-- 表的结构 `sys_role`
--

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `description` varchar(512) DEFAULT NULL COMMENT '角色职责描述',
  `permission_ids` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统角色表' AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `sys_role`
--

INSERT INTO `sys_role` (`id`, `name`, `description`, `permission_ids`) VALUES
(1, '管理员', '超管', '2,3,4'),
(2, '普通用户', '普通用户', '5');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(512) NOT NULL COMMENT '用户名，唯一',
  `password` varchar(128) NOT NULL COMMENT '密码',
  `nickname` varchar(128) NOT NULL COMMENT '昵称',
  `mobile` varchar(11) DEFAULT NULL COMMENT '手机号，唯一',
  `email` varchar(128) DEFAULT NULL COMMENT '邮箱，唯一',
  `status` tinyint(1) NOT NULL DEFAULT '0' COMMENT '0正常 1禁用',
  `create_time` int(10) NOT NULL COMMENT '创建时间',
  `update_time` int(10) NOT NULL COMMENT '生成时间',
  `last_login_time` int(10) NOT NULL COMMENT '最后登录时间',
  `role_ids` varchar(1024) DEFAULT NULL COMMENT '用户角色列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统用户表' AUTO_INCREMENT=2 ;

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `mobile`, `email`, `status`, `create_time`, `update_time`, `last_login_time`, `role_ids`) VALUES
(1, 'admin', '$2a$10$alTKssD4xMqs62Gp0apyjepPKNylFCGCh0xa1nXKAzrp1GgQF1S6y', '测试用户', '15238389368', '1256174071@qq.com', 0, 0, 0, 0, '1,2');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
