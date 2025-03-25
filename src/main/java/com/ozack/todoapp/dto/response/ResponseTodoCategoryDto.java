package com.ozack.todoapp.dto.response;

import com.ozack.todoapp.repository.entity.Category;

/* フロントエンド側にレスポンスする TodoCategory データ */
public record ResponseTodoCategoryDto(
    Long id,
    Category category
){}
