package com.ozack.todoapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozack.todoapp.dto.response.ResponseTodoDto;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.Todo;
import com.ozack.todoapp.service.todo.TodoService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/* 任意のユーザーが登録した Todo を操作するコントローラー */
@RequestMapping("todo")
@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    /* データの取得 */
    @GetMapping
    public ResponseEntity<List<ResponseTodoDto>> getTodos(@RequestParam Long userId) {
        List<ResponseTodoDto> res = todoService.selectAllTodosByUserIdWithCategories(userId);
        return ResponseEntity.ok().body(res);
    }

    /* データの登録 */
    @PostMapping
    public ResponseEntity<ResponseTodoDto> postTodo(@RequestBody Todo req) throws TodoAppException {
        ResponseTodoDto res = todoService.insertTodo(req);
        URI location = URI.create("/todo/" + res.id());
        return ResponseEntity.created(location).body(res);
    }

    /* データの更新 */
    @PutMapping
    public ResponseEntity<ResponseTodoDto> putTodo(@RequestBody Todo req) throws TodoAppException {
        ResponseTodoDto res = todoService.updateTodo(req);
        return ResponseEntity.ok().body(res);
    }

    /* データの削除 */
    @DeleteMapping
    public ResponseEntity<Void> deleteTodo(@RequestParam Long id) throws TodoAppException {
        todoService.deleteTodo(id);
        return ResponseEntity.noContent().build();
    }

}
