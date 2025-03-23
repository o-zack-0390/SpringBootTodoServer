-- カテゴリー
INSERT INTO `category`
 (`id`, `name`, `created_at`, `updated_at`)
VALUES
 (1, 'category-name-1', '2025-05-17 07:04:10', '2025-05-17 07:04:10'),
 (2, 'category-name-2', '2025-05-17 07:04:10', '2025-05-17 07:04:10');

 -- 権限
INSERT INTO `authority`
 (`id`, `name`, `created_at`, `updated_at`)
VALUES
 (1, 'authority-name-1', '2025-02-17 07:04:10', '2025-05-17 07:04:10');

-- ユーザー
INSERT INTO `user`
 (`id`, `authority_id`, `name`, `email`, `password`, `created_at`, `updated_at`)
VALUES
 (1, 1, 'user-name-1', 'user-email-1', 'user-password-1', '2025-02-17 07:04:10', '2025-05-17 07:04:10');

-- Todo
INSERT INTO `todo`
 (`id`, `user_id`, `title`, `is_check`, `created_at`, `updated_at`)
VALUES
 (1, 1, 'todo-title-1', 1, '2025-02-17 07:04:10', '2025-05-17 07:04:10'),
 (2, 1, 'todo-title-2', 0, '2025-02-17 07:04:10', '2025-05-17 07:04:10');
