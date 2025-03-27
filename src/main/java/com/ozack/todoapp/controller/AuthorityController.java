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
import com.ozack.todoapp.repository.entity.Authority;
import com.ozack.todoapp.service.authority.AuthorityService;

/* 権限情報を操作するコントローラー */
@RequestMapping("authority")
@RestController
public class AuthorityController {

    private final AuthorityService authorityService;

    public AuthorityController(AuthorityService authorityService) {
        this.authorityService = authorityService;
    }

    /* データの取得 */
    @GetMapping
    public ResponseEntity<List<Authority>> getAuthorities() {
        List<Authority> res = authorityService.selectAllAuthorities();
        return ResponseEntity.ok().body(res);
    }

    /* データの登録 */
    @PostMapping
    public ResponseEntity<Authority> postAuthoriry(@RequestBody Authority req) throws TodoAppException {
        Authority res = authorityService.insertAuthority(req);
        URI location = URI.create("/category/" + res.getId());
        return ResponseEntity.created(location).body(res);
    }

    /* データの更新 */
    @PutMapping
    public ResponseEntity<Authority> putAuthoriry(@RequestBody Authority req) throws TodoAppException {
        Authority res = authorityService.insertAuthority(req);
        return ResponseEntity.ok().body(res);
    }

    /* データの削除 */
    @DeleteMapping
    public ResponseEntity<Void> deleteAuthority(@RequestParam Long id) throws TodoAppException {
        authorityService.deleteAuthority(id);
        return ResponseEntity.noContent().build();
    }

}
