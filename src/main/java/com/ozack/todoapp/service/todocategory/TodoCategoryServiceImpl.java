package com.ozack.todoapp.service.todocategory;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.TodoCategoryRepository;
import com.ozack.todoapp.repository.entity.TodoCategory;

/* Todo と Category のマッピング情報を処理するサービス */
@Service
public class TodoCategoryServiceImpl implements TodoCategoryService {

    private final String loadErrorMessage = "todo_category テーブルのデータ取得に失敗しました。";
    private final String insertErrorMessageByDataAccess = "todo_category データ登録時にデータベース関連のエラーが発生しました。";
    private final String updateErrorMessageByDataAccess = "todo_category データ更新時にデータベース関連のエラーが発生しました。";
    private final String deleteErrorMessageByLoad = "todo_category データを削除できませんでした。";

    private final TodoCategoryRepository todoCategoryRepository;

    public TodoCategoryServiceImpl(TodoCategoryRepository todoCategoryRepository) {
        this.todoCategoryRepository = todoCategoryRepository;
    }

    /* 指定の todoId が存在する全ての TodoCategory データを取得するメソッド */
    @Override
    public List<TodoCategory> selectAllCategoriesByTodoIdWithCategories(Long todoId) {
        return todoCategoryRepository.findAllByTodoIdWithCategories(todoId);
    }

    /* データを登録するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public List<TodoCategory> insertTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException {
        try {
            List<TodoCategory> res = todoCategoryRepository.saveAll(todoCategories);
            if (res == null) throw new InsertException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new InsertException(insertErrorMessageByDataAccess, e);
        }
    }

    /* データを更新するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public List<TodoCategory> updateTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException {
        try {
            List<TodoCategory> res = todoCategoryRepository.saveAll(todoCategories);
            if (res == null) throw new UpdateException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new UpdateException(updateErrorMessageByDataAccess, e);
        }
    }

    /* データを削除するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public void deleteTodoCategories(List<Long> todoCategoryIds) throws TodoAppException {
        todoCategoryRepository.deleteAllById(todoCategoryIds);
        List<TodoCategory> res = todoCategoryRepository.findAllById(todoCategoryIds);
        if (!res.isEmpty()) throw new DeleteException(deleteErrorMessageByLoad);
    }

}
