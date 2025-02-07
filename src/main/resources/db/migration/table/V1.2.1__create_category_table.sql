-- カテゴリー情報
CREATE TABLE `category`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'カテゴリーID',
    `name`       varchar(16) NOT NULL COMMENT 'カテゴリー名',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (`id`),
    UNIQUE (`name`)
) ENGINE=InnoDB
