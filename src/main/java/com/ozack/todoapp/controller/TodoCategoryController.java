package com.ozack.todoapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozack.todoapp.dto.response.ResponseTodoCategoryDto;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.TodoCategory;
import com.ozack.todoapp.service.todocategory.TodoCategoryService;

/* Todo と Category のマッピング情報を操作するコントローラー */
@RequestMapping("todocategory")
@RestController
public class TodoCategoryController {

    private final TodoCategoryService todoCategoryService;

    public TodoCategoryController(TodoCategoryService todoCategoryService) {
        this.todoCategoryService = todoCategoryService;
    }

    /* データの登録 */
    @PostMapping
    public ResponseEntity<List<ResponseTodoCategoryDto>> postTodoCateories(
        @RequestBody List<TodoCategory> req) throws TodoAppException
    {
        List<ResponseTodoCategoryDto> res = todoCategoryService.insertTodoCategories(req);
        URI location = URI.create("/todocategories");
        return ResponseEntity.created(location).body(res);
    }

    /* データの更新 */
    @PutMapping
    public ResponseEntity<List<ResponseTodoCategoryDto>> putTodoCateories(
        @RequestBody List<TodoCategory> req) throws TodoAppException
    {
        List<ResponseTodoCategoryDto> res = todoCategoryService.updateTodoCategories(req);
        return ResponseEntity.ok().body(res);
    }

    /* データの削除 */
    @DeleteMapping
    public ResponseEntity<Void> deleteTodoCateories(@RequestParam List<Long> ids) throws TodoAppException {
        todoCategoryService.deleteTodoCategories(ids);
        return ResponseEntity.noContent().build();
    }

}
