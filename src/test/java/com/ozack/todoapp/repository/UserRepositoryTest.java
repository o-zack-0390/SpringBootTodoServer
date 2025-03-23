package com.ozack.todoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseUserDto;
import com.ozack.todoapp.repository.entity.Authority;
import com.ozack.todoapp.repository.entity.User;

/* UserRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class UserRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepository userRepository;

    /* 1. 指定の email を保持するデータの取得を試みる. */
    @Test
    @Sql({"/db/migration/repository/user/common.sql", "/db/migration/repository/user/read.sql"})
    public void test_findByEmailWithAuthority() {

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
        ResponseUserDto actual = userRepository.findByEmailWithAuthority(expected.email()).orElse(null);
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.id(), actual.id());
        assertEquals(expected.name(), actual.name());
        assertEquals(expected.email(), actual.email());
        assertEquals(expected.authority().getId(), actual.authority().getId());
        assertEquals(expected.authority().getName(), actual.authority().getName());
    }

    /* 2. データの登録を試みる */
    @Test
    @Sql("/db/migration/repository/user/common.sql")
    public void test_insert() {

        User expected = new User(
            null,
            1L,
            "user-name-1",
            "user-email-1",
            "user-password-1"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        User savedEntity = userRepository.save(expected);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        User actual = userRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(actual.getId());
        assertEquals(expected.getAuthorityId(), actual.getAuthorityId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    /* 3. データの更新を試みる */
    @Test
    @Sql({"/db/migration/repository/user/common.sql", "/db/migration/repository/user/update.sql"})
    public void test_update() {

        User expected = new User(
            1L,
            2L,
            "user-name-2",
            "user-email-2",
            "user-password-2"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        User savedEntity = userRepository.save(expected);
        logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));

        User actual = userRepository.findById(savedEntity.getId()).orElse(null);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getAuthorityId(), actual.getAuthorityId());
        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getEmail(), actual.getEmail());
        assertEquals(expected.getPassword(), actual.getPassword());
    }

    /* 4. データの削除を試みる */
    @Test
    @Sql({"/db/migration/repository/user/common.sql", "/db/migration/repository/user/delete.sql"})
    public void test_deleteById() {

        Long id = 1L;

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        userRepository.deleteById(id);
        logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));

        User actual = userRepository.findById(id).orElse(null);
        assertEquals(null, actual);
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
