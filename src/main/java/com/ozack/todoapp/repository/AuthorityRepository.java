package com.ozack.todoapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.repository.entity.Authority;

/* 権限情報を格納している authority テーブルと対応 */
@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {
}
