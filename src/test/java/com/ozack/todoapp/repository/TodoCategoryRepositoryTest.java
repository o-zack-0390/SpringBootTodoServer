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

import com.ozack.todoapp.repository.entity.TodoCategory;

/* TodoCategoryRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoCategoryRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TodoCategoryRepository todoCategoryRepository;

    /* 1. 指定の todoId を保持する全データの取得を試みる */
    @Test
    @Sql({"/db/migration/repository/todocategory/common.sql", "/db/migration/repository/todocategory/read.sql"})
    public void test_findAllByTodoIdWithCategories() {

        List<TodoCategory> expected = new ArrayList<>();
        expected.add(new TodoCategory(2L, 1L, 2L));
        expected.add(new TodoCategory(1L, 1L, 1L));

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<TodoCategory> actual = todoCategoryRepository.findAllByTodoIdWithCategories(1L);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        TodoCategory expected1 = expected.get(0);
        TodoCategory expected2 = expected.get(1);
        TodoCategory actual1 = actual.get(0);
        TodoCategory actual2 = actual.get(1);

        assertNotNull(actual1.getId());
        assertEquals(expected1.getTodoId(), actual1.getTodoId());
        assertEquals(expected1.getCategoryId(), actual1.getCategoryId());
        assertEquals("category-name-2", actual1.getCategory().getName());
        assertNotNull(actual2.getId());
        assertEquals(expected2.getTodoId(), actual2.getTodoId());
        assertEquals(expected2.getCategoryId(), actual2.getCategoryId());
        assertEquals("category-name-1", actual2.getCategory().getName());
    }

    /* 2. データの登録を試みる */
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

    /* 3. データの更新を試みる. */
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

    /* 4. データの削除を試みる. */
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
    1. 指定の todoId を保持する全データの取得を試みる
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. 指定の todoId を保持する全データの取得を試みる
    2. 該当データを登録
    3. 該当データを更新
    4. 該当データを削除
*/
