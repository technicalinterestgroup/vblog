-- auto Generated on 2019-08-25 15:12:15 
-- DROP TABLE IF EXISTS `file_upload`; 
CREATE TABLE `file_upload`(
    `user_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'userName',
    `file_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'fileName',
    `new_file_name` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'newFileName',
    `file_path` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'filePath',
    `file_size` VARCHAR (50) NOT NULL DEFAULT '' COMMENT 'fileSize',
    `id` BIGINT (15) NOT NULL AUTO_INCREMENT COMMENT 'id',
    `is_del` SMALLINT (5) NOT NULL DEFAULT -1 COMMENT 'isDel',
    `create_time` DATETIME NOT NULL DEFAULT '1000-01-01 00:00:00' COMMENT 'createTime',
    `update_time` TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'updateTime',
    PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT '`file_upload`';
