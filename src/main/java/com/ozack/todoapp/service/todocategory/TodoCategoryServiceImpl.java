package com.ozack.todoapp.service.todocategory;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseTodoCategoryDto;
import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.TodoCategoryRepository;
import com.ozack.todoapp.repository.entity.Category;
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

    /* データを登録するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public List<ResponseTodoCategoryDto> insertTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException {
        try {
            List<TodoCategory> resTodoCategories = todoCategoryRepository.saveAll(todoCategories);
            List<ResponseTodoCategoryDto> res = convertResponseTodoCategoryDto(resTodoCategories);
            if (res == null) throw new InsertException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new InsertException(insertErrorMessageByDataAccess, e);
        }
    }

    /* データを更新するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public List<ResponseTodoCategoryDto> updateTodoCategories(List<TodoCategory> todoCategories) throws TodoAppException {
        try {
            List<TodoCategory> resTodoCategories = todoCategoryRepository.saveAll(todoCategories);
            List<ResponseTodoCategoryDto> res = convertResponseTodoCategoryDto(resTodoCategories);
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

    /* Dto 変換メソッド */
    public List<ResponseTodoCategoryDto> convertResponseTodoCategoryDto(List<TodoCategory> todoCategories) {
        return todoCategories
            .stream()
            .map(todoCategory ->
                new ResponseTodoCategoryDto(
                    todoCategory.getId(),
                    new Category(todoCategory.getCategoryId(), null)
                )
            )
            .toList();
    }

}
