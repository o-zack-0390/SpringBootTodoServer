package com.ozack.todoapp.service.category;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.CategoryRepository;
import com.ozack.todoapp.repository.entity.Category;

/* カテゴリーに関する処理を行うサービス */
@Service
public class CategoryServiceImpl implements CategoryService {

    private final String loadErrorMessage = "category テーブルのデータ取得に失敗しました。";
    private final String insertErrorMessageByDataAccess = "category データ登録時にデータベース関連のエラーが発生しました。";
    private final String updateErrorMessageByDataAccess = "category データ更新時にデータベース関連のエラーが発生しました。";

    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /* 全てのカテゴリー一覧を取得するメソッド */
    public List<Category> selectAllCategories() {
        return categoryRepository.findAll();
    }

    /* データを登録するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Category insertCategory(Category category) throws TodoAppException {
        try {
            categoryRepository.save(category);
            Category res = categoryRepository.findById(category.getId()).orElse(null);
            if (res == null) throw new InsertException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new InsertException(insertErrorMessageByDataAccess, e);
        }
    }

    /* データを更新するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Category updateCategory(Category category) throws TodoAppException {
        try {
            categoryRepository.save(category);
            Category res = categoryRepository.findById(category.getId()).orElse(null);
            if (res == null) throw new UpdateException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new UpdateException(updateErrorMessageByDataAccess, e);
        }
    }

    /* データを削除するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public void deleteCategory(Long categoryId) throws TodoAppException {
        categoryRepository.deleteById(categoryId);
        Category res = categoryRepository.findById(categoryId).orElse(null);
        if (res != null) throw new DeleteException(loadErrorMessage);
    }

}
