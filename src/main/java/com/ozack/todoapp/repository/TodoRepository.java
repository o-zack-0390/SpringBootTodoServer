package com.ozack.todoapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.repository.entity.Todo;

/* Todo を格納している todo テーブルと対応 */
@Repository
public interface TodoRepository extends JpaRepository<Todo, Long> {

    /* userId を検索キーとしてデータを取得 */
    @Query("""
        SELECT t
        FROM Todo t
        LEFT JOIN FETCH t.todoCategories tc
        LEFT JOIN FETCH tc.category c
        WHERE t.userId = :userId
        ORDER BY t.id DESC
    """)
    List<Todo> findAllByUserIdWithCategories(@Param("userId") Long userId);

}
