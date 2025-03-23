package com.ozack.todoapp.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseUserDto;
import com.ozack.todoapp.repository.UserRepository;
import com.ozack.todoapp.repository.entity.Authority;
import com.ozack.todoapp.repository.entity.User;

/* UserService のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    /* 1. 指定の email を保持するデータの取得を試みる. */
    @Test
    @Sql({"/db/migration/service/user/common.sql", "/db/migration/service/user/read.sql"})
    public void test_selectUsersByEmailWithAuthorityUser() {

        ResponseUserDto expected = new ResponseUserDto(
            1L,
            "user-name-1",
            "user-email-1",
            new Authority(
                1L,
                "authority-name-1"
            )
        );

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        ResponseUserDto actual = userService.selectUsersByEmailWithAuthorityUser(expected.email());
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.email(), actual.email());
        assertEquals(expected.authority().getId(), actual.authority().getId());
        assertEquals(expected.authority().getName(), actual.authority().getName());
    }

    /* 2. データの登録を試みる */
    @Test
    @Sql("/db/migration/service/user/common.sql")
    public void test_insertUser() {

        // 期待値
        ResponseUserDto expected = new ResponseUserDto(
            null,
            "user-name-1",
            "user-email-1",
            new Authority(
                1L,
                "authority-name-1"
            )
        );

        // 登録データ
        User insertData = new User(
            null,
            1L,
            "user-name-1",
            "user-email-1",
            "user-password-1"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            ResponseUserDto actual = userService.insertUser(insertData);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.id());
            assertEquals(expected.name(), actual.name());
            assertEquals(expected.email(), actual.email());
            assertEquals(expected.authority().getId(), actual.authority().getId());
            assertEquals(expected.authority().getName(), actual.authority().getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 3. データの更新を試みる */
    @Test
    @Sql({"/db/migration/service/user/common.sql", "/db/migration/service/user/update.sql"})
    public void test_updateUser() {

        // 期待値
        ResponseUserDto expected = new ResponseUserDto(
            null,
            "user-name-2",
            "user-email-2",
            new Authority(
                2L,
                "authority-name-2"
            )
        );

        // 更新データ
        User updateData = new User(
            1L,
            2L,
            "user-name-2",
            "user-email-2",
            "user-password-2"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            ResponseUserDto actual = userService.updateUser(updateData);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.id());
            assertEquals(expected.name(), actual.name());
            assertEquals(expected.email(), actual.email());
            assertEquals(expected.authority().getId(), actual.authority().getId());
            assertEquals(expected.authority().getName(), actual.authority().getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 4. データの削除を試みる */
    @Test
    @Sql({"/db/migration/service/user/common.sql", "/db/migration/service/user/delete.sql"})
    public void test_deleteUser() {

        Long id = 1L;

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            userService.deleteUser(id);
            logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            User actual = userRepository.findById(id).orElse(null);
            assertEquals(null, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}

/*
    想定場面
    1. 指定の email を保持する User データの取得を試みる.
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. 該当する User データを取得.
    2. 該当データを登録
    4. 該当データを更新
    3. 該当データを削除
*/
