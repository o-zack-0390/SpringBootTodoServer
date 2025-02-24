package com.ozack.todoapp.dto.response;

import java.util.List;

import com.ozack.todoapp.repository.entity.Category;

/* フロントエンド側にレスポンスする Todo データ */
public record ResponseTodoDto(
    Long id,
    String title,
    Boolean isCheck,
    List<Category> categories
){}
