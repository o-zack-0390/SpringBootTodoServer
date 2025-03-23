package com.ozack.todoapp.service.todo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseTodoDto;
import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.TodoRepository;
import com.ozack.todoapp.repository.entity.Category;
import com.ozack.todoapp.repository.entity.Todo;

/* カテゴリーに関する処理を行うサービス */
@Service
public class TodoServiceImpl implements TodoService {

    private final String loadErrorMessage = "todo テーブルのデータ取得に失敗しました。";
    private final String insertErrorMessageByDataAccess = "todo データ登録時にデータベース関連のエラーが発生しました。";
    private final String insertErrorMessageByRuntime = "todo データ登録時に予期しないエラーが発生しました。";
    private final String updateErrorMessageByDataAccess = "todo データ更新時にデータベース関連のエラーが発生しました。";
    private final String updateErrorMessageByRuntime = "todo データ更新時に予期しないエラーが発生しました。";

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    /* 全てのカテゴリー一覧を取得するメソッド */
    public List<ResponseTodoDto> selectAllTodosByUserIdWithCategories(Long userId) {
        List<Todo> res = todoRepository.findAllByUserIdWithCategories(userId);
        return convertResponseTodoDto(res);
    }

    /* データを登録するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Todo insertTodo(Todo todo) throws TodoAppException {
        try {
            Todo res = todoRepository.save(todo);
            if (res == null) throw new InsertException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new InsertException(insertErrorMessageByDataAccess, e);
        } catch (RuntimeException e) {
            throw new InsertException(insertErrorMessageByRuntime, e);
        } catch (TodoAppException e) {
            throw new TodoAppException(loadErrorMessage, e);
        }
    }

    /* データを更新するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Todo updateTodo(Todo todo) throws TodoAppException {
        try {
            Todo res = todoRepository.save(todo);
            if (res == null) throw new UpdateException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new UpdateException(updateErrorMessageByDataAccess, e);
        } catch (RuntimeException e) {
            throw new UpdateException(updateErrorMessageByRuntime, e);
        } catch (TodoAppException e) {
            throw new TodoAppException(loadErrorMessage, e);
        }
    }

    /* データを削除するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public void deleteTodo(Long todoId) throws TodoAppException {
        todoRepository.deleteById(todoId);
        Todo res = todoRepository.findById(todoId).orElse(null);
        if (res != null) throw new DeleteException(loadErrorMessage);
    }

    /* リスト型の Todo エンティティをリスト型の Dto に変換するメソッド */
    public List<ResponseTodoDto> convertResponseTodoDto(List<Todo> todos) {
        return todos.stream()
                    .map(todo -> new ResponseTodoDto(
                            todo.getId(),
                            todo.getTitle(),
                            todo.getIsCheck(),
                            todo.getTodoCategories() == null ? new ArrayList<>() : // カテゴリーが設定されてない場合は空リストを設定
                            todo.getTodoCategories()
                                .stream()
                                .sorted(Comparator.comparing(todoCategory -> todoCategory.getCategory().getName())) // 名前順にソート
                                .map(todoCategory -> new Category( // カテゴリー型に変換
                                    todoCategory.getCategoryId(),
                                    todoCategory.getCategory().getName()
                                ))
                                .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
    }

}
