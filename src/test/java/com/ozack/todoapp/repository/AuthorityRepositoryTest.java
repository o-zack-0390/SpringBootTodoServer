package com.ozack.todoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.repository.entity.Authority;

/* AuthorityRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthorityRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthorityRepository authorityRepository;

    /* 1. 全ての Authority データの取得を試みる. */
    @Test
    @Sql("/db/migration/repository/authority/read.sql")
    public void test_findAll() {

        List<Authority> expected = new ArrayList<>();
        expected.add(new Authority(1L, "authority-name-1"));
        expected.add(new Authority(2L, "authority-name-2"));

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<Authority> actual = authorityRepository.findAll();
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
    }

    /* 2. データの登録を試みる */
    @Test
    public void test_insert() {

        Authority expected = new Authority(
            null,
            "authority-name-1"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Authority savedEntity = authorityRepository.save(expected);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        Authority actual = authorityRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    /* 3. データの更新を試みる */
    @Test
    @Sql("/db/migration/repository/authority/update.sql")
    public void test_update() {

        Authority expected = new Authority(
            1L,
            "authority-name-2"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Authority savedEntity = authorityRepository.save(expected);
        logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));

        Authority actual = authorityRepository.findById(savedEntity.getId()).orElse(null);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    /* 4. データの削除を試みる */
    @Test
    @Sql("/db/migration/repository/authority/update.sql")
    public void test_deleteById() {

        Long id = 1L;

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        authorityRepository.deleteById(id);
        logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));

        Authority actual = authorityRepository.findById(id).orElse(null);
        assertEquals(null, actual);
    }

}

/*
    想定場面
    1. 全ての Authority データの取得を試みる.
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. Authority データを全て取得.
    2. 該当データを登録
    4. 該当データを更新
    3. 該当データを削除
*/
