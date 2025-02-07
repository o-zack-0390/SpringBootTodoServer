-- Todo 情報
CREATE TABLE `todo`
(
    `id`         bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'TodoID',
    `user_id`    bigint unsigned NOT NULL COMMENT 'ユーザーID',
    `title`      varchar(32) NOT NULL COMMENT 'やること内容',
    `is_check`   tinyint(1) NOT NULL COMMENT 'チェックの有無を示す真偽値',
    `created_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES `user`(`id`) ON DELETE CASCADE,
    UNIQUE (`user_id`, `title`)
) ENGINE=InnoDB
