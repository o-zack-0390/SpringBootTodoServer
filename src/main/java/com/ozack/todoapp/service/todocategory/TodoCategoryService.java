package com.ozack.todoapp.service.todocategory;

import java.util.List;

import com.ozack.todoapp.dto.response.ResponseTodoCategoryDto;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.TodoCategory;

/* Todo と Category のマッピング情報を処理するサービス */
public interface TodoCategoryService {

    /* データを登録するメソッド */
    public List<ResponseTodoCategoryDto> insertTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException;

    /* データを更新するメソッド */
    public List<ResponseTodoCategoryDto> updateTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteTodoCategories(List<Long> todoCategoryIds) throws TodoAppException;

}
