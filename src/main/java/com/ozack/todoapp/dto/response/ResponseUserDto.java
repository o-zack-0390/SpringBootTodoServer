package com.ozack.todoapp.dto.response;

import com.ozack.todoapp.repository.entity.Authority;

/* フロントエンド側にレスポンスする User データ */
public record ResponseUserDto(
    Long id,
    String name,
    String email,
    Authority authority
){}
