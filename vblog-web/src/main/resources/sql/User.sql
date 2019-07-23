CREATE TABLE `user` (
  `id` bigint(15) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `user_name` varchar(50) NOT NULL DEFAULT '' COMMENT '用户名',
  `pass_word` varchar(50) NOT NULL DEFAULT '' COMMENT '密码',
  `email` varchar(50) NOT NULL DEFAULT '' COMMENT '邮箱',
  `photo` varchar(50) NOT NULL DEFAULT '' COMMENT '照片',
  `integral` int(11) NOT NULL DEFAULT '-1' COMMENT '积分',
  `state` smallint(5) NOT NULL DEFAULT '0' COMMENT '状态0：待激活，1：待激活',
  `is_del` smallint(5) NOT NULL DEFAULT '0' COMMENT '删除0：未删除',
  `create_time` datetime NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT '创建时间',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='用户表';