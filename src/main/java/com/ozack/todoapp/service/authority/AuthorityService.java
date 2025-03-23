package com.ozack.todoapp.service.authority;

import java.util.List;

import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.entity.Authority;

/* 権限に関する処理を行うサービス */
public interface AuthorityService {

    /* 全ての権限一覧を取得するメソッド */
    public List<Authority> selectAllAuthorities();

    /* データを登録するメソッド */
    public Authority insertAuthority(Authority authority) throws TodoAppException;

    /* データを更新するメソッド */
    public Authority updateAuthority(Authority authority) throws TodoAppException;

    /* データを削除するメソッド */
    public void deleteAuthority(Long authorityId) throws TodoAppException;

}
