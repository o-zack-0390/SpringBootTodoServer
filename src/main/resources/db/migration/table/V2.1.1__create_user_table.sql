-- ユーザー情報
CREATE TABLE `user`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ユーザーID',
    `authority_id` bigint unsigned NOT NULL COMMENT '権限ID',
    `name`         varchar(16) NOT NULL COMMENT 'ユーザー名',
    `email`        varchar(32) NOT NULL COMMENT 'メールアドレス',
    `password`     varchar(32) NOT NULL COMMENT 'パスワード',
    `created_at`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`authority_id`) REFERENCES `authority`(`id`) ON DELETE CASCADE,
    UNIQUE (`email`)
) ENGINE=InnoDB
