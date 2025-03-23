package com.ozack.todoapp.service.todocategory;

import java.util.List;

import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.TodoCategory;

/* Todo と Category のマッピング情報を処理するサービス */
public interface TodoCategoryService {

    /* 指定の todoId が存在する全ての TodoCategory データを取得するメソッド */
    public List<TodoCategory> selectAllCategoriesByTodoIdWithCategories(Long todoId);

    /* データを登録するメソッド */
    public List<TodoCategory> insertTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException;

    /* データを更新するメソッド */
    public List<TodoCategory> updateTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteTodoCategory(Long todoCategoryId) throws TodoAppException;

}
