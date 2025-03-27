package com.ozack.todoapp.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.Category;
import com.ozack.todoapp.service.category.CategoryService;

/* カテゴリー情報を操作するコントローラー */
@RequestMapping("category")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    /* データの取得 */
    @GetMapping
    public ResponseEntity<List<Category>> getCategories() {
        List<Category> res = categoryService.selectAllCategories();
        return ResponseEntity.ok().body(res);
    }

    /* データの登録 */
    @PostMapping
    public ResponseEntity<Category> postCategory(@RequestBody Category req) throws TodoAppException {
        Category res = categoryService.insertCategory(req);
        URI location = URI.create("/category/" + res.getId());
        return ResponseEntity.created(location).body(res);
    }

    /* データの更新 */
    @PutMapping
    public ResponseEntity<Category> putCategory(@RequestBody Category req) throws TodoAppException {
        Category res = categoryService.updateCategory(req);
        return ResponseEntity.ok().body(res);
    }

    /* データの削除 */
    @DeleteMapping
    public ResponseEntity<Void> deleteCategory(@RequestParam Long id) throws TodoAppException {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }

}
