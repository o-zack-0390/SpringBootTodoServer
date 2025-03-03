package com.ozack.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.repository.entity.TodoCategory;

/* Todo と Category のマッピング情報を格納している todo_category テーブルと対応 */
@Repository
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, Long> {
}
