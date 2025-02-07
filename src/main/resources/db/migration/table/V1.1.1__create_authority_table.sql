-- 権限情報
CREATE TABLE `authority`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '権限ID',
    `name`       varchar(16) NOT NULL COMMENT '権限名',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE=InnoDB
