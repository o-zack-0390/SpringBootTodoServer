package com.ozack.todoapp.service.category;

import java.util.List;

import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.Category;

/* カテゴリーに関する処理を行うサービス */
public interface CategoryService {

    /* 全てのカテゴリー一覧を取得するメソッド */
    public List<Category> selectAllCategories();

    /* データを登録するメソッド */
    public Category insertCategory(Category category) throws TodoAppException;

    /* データを更新するメソッド */
    public Category updateCategory(Category category) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteCategory(Long categoryId) throws TodoAppException;

}
