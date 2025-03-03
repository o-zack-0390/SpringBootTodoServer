package com.ozack.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.repository.entity.Category;

/* カテゴリー情報を格納している category テーブルと対応 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
