-- auto Generated on 2019-08-18 17:48:30 
-- DROP TABLE IF EXISTS `v_comment`; 
CREATE TABLE `v_comment`(
    `comment_content` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'commentContent',
    `user_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'userName',
    `ip_address` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'ipAddress',
    `volt_number` BIGINT (15) NOT NULL DEFAULT -1 COMMENT 'voltNumber',
    `article_id` BIGINT (15) NOT NULL DEFAULT -1 COMMENT 'articleId',
    `parent_id` BIGINT (15) NOT NULL DEFAULT -1 COMMENT 'parentId',
    `is_auther` SMALLINT (5) NOT NULL DEFAULT -1 COMMENT 'isAuther',
    `id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `is_del` SMALLINT (5) NOT NULL DEFAULT -1 COMMENT 'isDel',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'createTime',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`v_comment`';
