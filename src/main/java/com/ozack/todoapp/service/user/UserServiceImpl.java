package com.ozack.todoapp.service.user;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseUserDto;
import com.ozack.todoapp.exception.DeleteException;
import com.ozack.todoapp.exception.InsertException;
import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.exception.UpdateException;
import com.ozack.todoapp.repository.UserRepository;
import com.ozack.todoapp.repository.entity.User;

/* ログインユーザーに関連する情報を処理するサービス */
@Service
public class UserServiceImpl implements UserService {

    private final String loadErrorMessage = "user テーブルのデータ取得に失敗しました。";
    private final String insertErrorMessageByDataAccess = "user データ登録時にデータベース関連のエラーが発生しました。";
    private final String insertErrorMessageByRuntime = "user データ登録時に予期しないエラーが発生しました。";
    private final String updateErrorMessageByDataAccess = "user データ更新時にデータベース関連のエラーが発生しました。";
    private final String updateErrorMessageByRuntime = "user データ更新時に予期しないエラーが発生しました。";

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /* ログインユーザーの情報を取得するメソッド */
    @Override
    public ResponseUserDto selectUsersByEmailWithAuthorityUser(String email) {
        return userRepository.findByEmailWithAuthority(email).orElse(null);
    }

    /* データを登録するメソッド */
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public ResponseUserDto insertUser(User user) throws TodoAppException {
        try {
            userRepository.save(user);
            ResponseUserDto res = userRepository.findByEmailWithAuthority(user.getEmail()).orElse(null);
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
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public ResponseUserDto updateUser(User user) throws TodoAppException {
        try {
            userRepository.save(user);
            ResponseUserDto res = userRepository.findByEmailWithAuthority(user.getEmail()).orElse(null);
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
    @Override
    @Transactional(rollbackFor = TodoAppException.class)
    public void deleteUser(Long userId) throws TodoAppException {
        userRepository.deleteById(userId);
        User res = userRepository.findById(userId).orElse(null);
        if (res != null) throw new DeleteException(loadErrorMessage);
    }

}
