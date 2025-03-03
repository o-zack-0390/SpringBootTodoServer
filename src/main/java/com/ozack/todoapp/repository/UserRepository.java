package com.ozack.todoapp.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.ozack.todoapp.dto.response.ResponseUserDto;
import com.ozack.todoapp.repository.entity.User;

/* ユーザー情報を格納している user テーブルと対応 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /* メールアドレスを検索キーとしてユーザー情報を取得 */
    @Query("""
        SELECT new com.ozack.todoapp.dto.response.ResponseUserDto(
            u.id,
            u.name,
            u.email,
            new com.ozack.todoapp.repository.entity.Authority(
                a.id,
                a.name
            )
        )
        FROM User u
        LEFT JOIN u.authority a
        WHERE u.email = :email
            """)
    Optional<ResponseUserDto> findByEmailWithAuthority(@Param("email") String email);

}
