-- todo と category のマッピング情報
CREATE TABLE `todo_category`
(
    `id`           bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'マッピングID',
    `todo_id`      bigint unsigned NOT NULL COMMENT 'TodoID',
    `category_id`  bigint unsigned NOT NULL COMMENT 'カテゴリーID',
    `created_at`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '作成日時',
    `updated_at`   datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新日時',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`todo_id`) REFERENCES `todo`(`id`) ON DELETE CASCADE,
    FOREIGN KEY (`category_id`) REFERENCES `category`(`id`) ON DELETE CASCADE,
    UNIQUE (`todo_id`, `category_id`)
) ENGINE=InnoDB
