-- Todo
INSERT INTO `todo`
 (`id`, `user_id`, `title`, `is_check`, `created_at`, `updated_at`)
VALUES
 (1, 1, 'todo-title-1', 1, '2025-02-17 07:04:10', '2025-05-17 07:04:10'),
 (2, 1, 'todo-title-2', 0, '2025-02-17 07:04:10', '2025-05-17 07:04:10');

-- todo と category のマッピング情報
INSERT INTO `todo_category`
 (`id`, `todo_id`, `category_id`, `created_at`, `updated_at`)
VALUES
 (1, 1, 1, '2025-02-17 07:04:10', '2025-05-17 07:04:10'),
 (2, 1, 2, '2025-02-17 07:04:10', '2025-05-17 07:04:10');
