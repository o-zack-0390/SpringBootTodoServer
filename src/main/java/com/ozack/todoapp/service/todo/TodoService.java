package com.ozack.todoapp.service.todo;

import java.util.List;

import com.ozack.todoapp.dto.response.ResponseTodoDto;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.Todo;

/* Todo に関する処理を行うサービス */
public interface TodoService {

    /* 任意ユーザーの Todo 一覧を取得するメソッド */
    public List<ResponseTodoDto> selectAllTodosByUserIdWithCategories(Long userId);

    /* データを登録するメソッド */
    public Todo insertTodo(Todo todo) throws TodoAppException;

    /* データを更新するメソッド */
    public Todo updateTodo(Todo todo) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteTodo(Long todoId) throws TodoAppException;

}
