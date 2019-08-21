-- auto Generated on 2019-08-21 13:11:19 
-- DROP TABLE IF EXISTS `v_collection`; 
CREATE TABLE `v_collection`(
    `user_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'userName',
    `article_id` BIGINT (15) NOT NULL DEFAULT 0 COMMENT 'articleId',
    `id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `is_del` SMALLINT (5) NOT NULL DEFAULT 0 COMMENT 'isDel',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'createTime',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`v_collection`';
