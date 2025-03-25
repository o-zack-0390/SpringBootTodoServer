package com.ozack.todoapp.dto.response;

import java.util.List;

/* フロントエンド側にレスポンスする Todo データ */
public record ResponseTodoDto(
    Long id,
    String title,
    Boolean isCheck,
    List<ResponseTodoCategoryDto> todoCategories
){}
