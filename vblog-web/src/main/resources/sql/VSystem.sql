-- auto Generated on 2019-08-14 13:08:06 
-- DROP TABLE IF EXISTS `v_system`; 
CREATE TABLE `v_system`(
    `user_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'userName',
    `v_title` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'vTitle',
    `v_icon` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'vIcon',
    `v_start` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'vStart',
    `seo_key_words` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'seoKeyWords',
    `v_footer_info` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'vFooterInfo',
    `v_introduct` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'vIntroduct',
    `module` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'module',
    `notice_switch` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'noticeSwitch',
    `comment_switch` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'commentSwitch',
    `id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `is_del` SMALLINT (5) NOT NULL DEFAULT -1 COMMENT 'isDel',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'createTime',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`v_system`';
ALTER TABLE v_system
	ADD theme VARCHAR(50) NOT NULL DEFAULT '' COMMENT 'theme';
