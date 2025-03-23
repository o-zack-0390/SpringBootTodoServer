package com.ozack.todoapp.service.user;

import com.ozack.todoapp.dto.response.ResponseUserDto;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.User;

/* ログインユーザーの情報を取得するサービス */
public interface UserService {

    /* ログインユーザーの情報を取得するメソッド */
    public ResponseUserDto selectUsersByEmailWithAuthorityUser(String email);

    /* データを登録するメソッド */
    public ResponseUserDto insertUser(User user) throws TodoAppException;

    /* データを更新するメソッド */
    public ResponseUserDto updateUser(User user) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteUser(Long userId) throws TodoAppException;

}
