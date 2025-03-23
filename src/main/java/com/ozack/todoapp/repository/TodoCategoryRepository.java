package com.ozack.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.repository.entity.TodoCategory;

/* Todo と Category のマッピング情報を格納している todo_category テーブルと対応 */
@Repository
public interface TodoCategoryRepository extends JpaRepository<TodoCategory, Long> {

    /* todoId を検索キーとしてデータを取得 */
    @Query("""
        SELECT tc
        FROM TodoCategory tc
        LEFT JOIN FETCH tc.category c
        WHERE tc.todoId = :todoId
        ORDER BY tc.id DESC
    """)
    List<TodoCategory> findAllByTodoIdWithCategories(@Param("todoId") Long todoId);

}
