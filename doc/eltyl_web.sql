-- phpMyAdmin SQL Dump
-- version phpStudy 2014
-- http://www.phpmyadmin.net
--
-- 主机: localhost
-- 生成日期: 2019 年 09 月 12 日 16:41
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

--
-- 转存表中的数据 `persistent_logins`
--

INSERT INTO `persistent_logins` (`username`, `series`, `token`, `last_used`) VALUES
('admin', 'atanhE38bRAZf6kSQ5+tkQ==', 'pHJ6Kz0m6l5xbeU5YXGPFw==', '2019-09-12 08:36:22'),
('admin', 'rf4xarL8RyUu86SWHt6Vdg==', 'BvUaRN6c9ZGN2W+iOKUzeA==', '2019-09-12 08:30:46');

-- --------------------------------------------------------

--
-- 表的结构 `sys_permission`
--

CREATE TABLE IF NOT EXISTS `sys_permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) NOT NULL DEFAULT '0',
  `name` varchar(128) NOT NULL COMMENT '权限名称',
  `code` varchar(128) NOT NULL COMMENT '权限码',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统权限表' AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `sys_permission`
--

INSERT INTO `sys_permission` (`id`, `pid`, `name`, `code`) VALUES
(1, 0, '管理商品', 'good:manage'),
(2, 1, '添加商品', 'good:add'),
(3, 1, '删除商品', 'good:del'),
(4, 1, '修改商品', 'good:update'),
(5, 1, '查看商品', 'good:view'),
(6, 0, '测试', 'test'),
(7, 6, '测试子类', '111');

-- --------------------------------------------------------

--
-- 表的结构 `sys_role`
--

CREATE TABLE IF NOT EXISTS `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(64) NOT NULL COMMENT '角色名',
  `code` varchar(64) DEFAULT NULL,
  `description` varchar(512) DEFAULT NULL COMMENT '角色职责描述',
  `permission_ids` text,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统角色表' AUTO_INCREMENT=8 ;

--
-- 转存表中的数据 `sys_role`
--

INSERT INTO `sys_role` (`id`, `name`, `code`, `description`, `permission_ids`) VALUES
(1, '管理员', 'admin', '超管222', '1,3,4,6'),
(2, '普通用户', 'user', '普通用户', '1,2,3,4,5,6'),
(4, 'vv', 'vv', 'dd', '1,5'),
(5, 'ff', 'ff', 'ff', '6'),
(6, 'aa', 'ss', 'dd', '1,2,3,4,5'),
(7, 'cc', 'cc', 'cc', '1,2,3,4,5');

-- --------------------------------------------------------

--
-- 表的结构 `sys_user`
--

CREATE TABLE IF NOT EXISTS `sys_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(512) NOT NULL COMMENT '用户名，唯一',
  `password` varchar(128) DEFAULT '' COMMENT '密码',
  `nickname` varchar(128) DEFAULT '' COMMENT '昵称',
  `mobile` varchar(11) DEFAULT '' COMMENT '手机号，唯一',
  `email` varchar(128) DEFAULT '' COMMENT '邮箱，唯一',
  `status` tinyint(1) DEFAULT '0' COMMENT '0正常 1禁用',
  `create_time` int(10) DEFAULT '0' COMMENT '创建时间',
  `update_time` int(10) DEFAULT '0' COMMENT '生成时间',
  `last_login_time` int(10) DEFAULT '0' COMMENT '最后登录时间',
  `role_ids` varchar(1024) DEFAULT '0' COMMENT '用户角色列表',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COMMENT='系统用户表' AUTO_INCREMENT=5 ;

--
-- 转存表中的数据 `sys_user`
--

INSERT INTO `sys_user` (`id`, `username`, `password`, `nickname`, `mobile`, `email`, `status`, `create_time`, `update_time`, `last_login_time`, `role_ids`) VALUES
(1, 'admin', '$2a$10$02BadqPykC9LOcMOsrQ5OurhKHJn435Lrsp2VkvnfWpmuOp2KAI9C', 'LPCTSTR', '15238389368', '1256174071@qq.com', 0, 1568182785, 1568257910, 1568277055, '4,2,1'),
(2, 'test', NULL, '测试', '12345', '123', 2, 1568182785, 1568199386, 1568182785, '2'),
(4, 'aaa', '', 'aaa', 'aaa', 'aaa', 0, 1568260032, 1568260039, 0, '2,4,5');

-- --------------------------------------------------------

--
-- 表的结构 `user`
--

CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_uuid` varchar(70) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `telephone` varchar(255) DEFAULT NULL,
  `role` int(10) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `last_ip` varchar(255) DEFAULT NULL,
  `last_time` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT AUTO_INCREMENT=3 ;

--
-- 转存表中的数据 `user`
--

INSERT INTO `user` (`id`, `user_uuid`, `username`, `password`, `email`, `telephone`, `role`, `image`, `last_ip`, `last_time`) VALUES
(2, '1234', 'admin', '$2a$10$alTKssD4xMqs62Gp0apyjepPKNylFCGCh0xa1nXKAzrp1GgQF1S6y', NULL, NULL, NULL, NULL, NULL, NULL);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
