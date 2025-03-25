package com.ozack.todoapp.service.authority;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.AuthorityRepository;
import com.ozack.todoapp.repository.entity.Authority;

/* 権限に関する処理を行うサービス */
@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final String loadErrorMessage = "authority テーブルのデータ取得に失敗しました。";
    private final String insertErrorMessageByDataAccess = "authority データ登録時にデータベース関連のエラーが発生しました。";
    private final String updateErrorMessageByDataAccess = "authority データ更新時にデータベース関連のエラーが発生しました。";

    private final AuthorityRepository authorityRepository;

    public AuthorityServiceImpl(AuthorityRepository authorityRepository) {
        this.authorityRepository = authorityRepository;
    }

    /* 全ての権限一覧を取得するメソッド */
    public List<Authority> selectAllAuthorities() {
        return authorityRepository.findAll();
    }

    /* データを登録するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Authority insertAuthority(Authority authority) throws TodoAppException {
        try {
            authorityRepository.save(authority);
            Authority res = authorityRepository.findById(authority.getId()).orElse(null);
            if (res == null) throw new InsertException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new InsertException(insertErrorMessageByDataAccess, e);
        }
    }

    /* データを更新するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public Authority updateAuthority(Authority authority) throws TodoAppException {
        try {
            authorityRepository.save(authority);
            Authority res = authorityRepository.findById(authority.getId()).orElse(null);
            if (res == null) throw new UpdateException(loadErrorMessage);
            return res;
        } catch (DataAccessException e) {
            throw new UpdateException(updateErrorMessageByDataAccess, e);
        }
    }

    /* データを削除するメソッド */
    @Transactional(rollbackFor = TodoAppException.class)
    public void deleteAuthority(Long authorityId) throws TodoAppException {
        authorityRepository.deleteById(authorityId);
        Authority res = authorityRepository.findById(authorityId).orElse(null);
        if (res != null) throw new DeleteException(loadErrorMessage);
    }

}
