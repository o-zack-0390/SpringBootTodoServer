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

import com.ozack.todoapp.repository.entity.TodoCategory;

/* TodoCategoryRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoCategoryRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TodoCategoryRepository todoCategoryRepository;

    /* 1. データの登録を試みる */
    @Test
    @Sql("/db/migration/repository/todocategory/common.sql")
    public void test_insert() {

        TodoCategory expected = new TodoCategory(
            null,
            1L,
            1L
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        TodoCategory savedEntity = todoCategoryRepository.save(expected);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        TodoCategory actual = todoCategoryRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(actual.getId());
        assertEquals(expected.getTodoId(), actual.getTodoId());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
    }

    /* 2. データの更新を試みる. */
    @Test
    @Sql({"/db/migration/repository/todocategory/common.sql", "/db/migration/repository/todocategory/update.sql"})
    public void test_update() {

        TodoCategory expected = new TodoCategory(
            1L,
            1L,
            2L
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        TodoCategory savedEntity = todoCategoryRepository.save(expected);
        logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));

        TodoCategory actual = todoCategoryRepository.findById(savedEntity.getId()).orElse(null);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getTodoId(), actual.getTodoId());
        assertEquals(expected.getCategoryId(), actual.getCategoryId());
    }

    /* 3. データの削除を試みる. */
    @Test
    @Sql({"/db/migration/repository/todocategory/common.sql", "/db/migration/repository/todocategory/delete.sql"})
    public void test_deleteById() {

        Long id = 1L;

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        todoCategoryRepository.deleteById(id);
        logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));

        TodoCategory actual = todoCategoryRepository.findById(id).orElse(null);
        assertEquals(null, actual);
    }

}

/*
    想定場面
    1. データの登録を試みる.
    2. データの更新を試みる.
    3. データの削除を試みる.

    期待処理
    1. 該当データを登録
    2. 該当データを更新
    3. 該当データを削除
*/
