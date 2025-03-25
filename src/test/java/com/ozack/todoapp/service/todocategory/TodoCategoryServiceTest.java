package com.ozack.todoapp.service.todocategory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.repository.TodoCategoryRepository;
import com.ozack.todoapp.repository.entity.TodoCategory;

/* TodoCategoryService のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoCategoryServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    TodoCategoryService todoCategoryService;

    @Autowired
    TodoCategoryRepository todoCategoryRepository;

    /* 1. データの取得を試みる */
    @Test
    @Sql({"/db/migration/service/todocategory/common.sql", "/db/migration/service/todocategory/read.sql"})
    public void test_selectAllByTodoIdWithCategories() {

        List<TodoCategory> expected = new ArrayList<>();
        expected.add(new TodoCategory(2L, 1L, 2L));
        expected.add(new TodoCategory(1L, 1L, 1L));

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<TodoCategory> actual = todoCategoryService.selectAllCategoriesByTodoIdWithCategories(1L);
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
    @Sql("/db/migration/service/todocategory/common.sql")
    public void test_insertTodoCategories() {

        List<TodoCategory> expected = new ArrayList<>();
        expected.add(new TodoCategory(null, 1L, 1L));
        expected.add(new TodoCategory(null, 1L, 2L));

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            todoCategoryService.insertTodoCategories(expected);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            List<TodoCategory> actual = todoCategoryRepository.findAll();
            assertNotNull(actual.get(0).getId());
            assertEquals(expected.get(0).getTodoId(), actual.get(0).getTodoId());
            assertEquals(expected.get(0).getCategoryId(), actual.get(0).getCategoryId());
            assertNotNull(actual.get(1).getId());
            assertEquals(expected.get(1).getTodoId(), actual.get(1).getTodoId());
            assertEquals(expected.get(1).getCategoryId(), actual.get(1).getCategoryId());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 3. データの更新を試みる. */
    @Test
    @Sql({"/db/migration/service/todocategory/common.sql", "/db/migration/service/todocategory/update.sql"})
    public void test_updateTodoCategories() {

        List<TodoCategory> expected = new ArrayList<>();
        expected.add(new TodoCategory(1L, 1L, 1L));
        expected.add(new TodoCategory(2L, 1L, 3L));

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            todoCategoryService.updateTodoCategories(expected);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            List<TodoCategory> actual = todoCategoryRepository.findAll();
            assertNotNull(actual.get(0).getId());
            assertEquals(expected.get(0).getTodoId(), actual.get(0).getTodoId());
            assertEquals(expected.get(0).getCategoryId(), actual.get(0).getCategoryId());
            assertNotNull(actual.get(1).getId());
            assertEquals(expected.get(1).getTodoId(), actual.get(1).getTodoId());
            assertEquals(expected.get(1).getCategoryId(), actual.get(1).getCategoryId());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 4. データの削除を試みる. */
    @Test
    @Sql({"/db/migration/service/todocategory/common.sql", "/db/migration/service/todocategory/delete.sql"})
    public void test_deleteTodoCategory() {

        List<Long> ids = Arrays.asList(1L, 2L);

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            todoCategoryService.deleteTodoCategories(ids);
            logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            List<TodoCategory> actual = todoCategoryRepository.findAllById(ids);
            assertTrue(actual.isEmpty());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}

/*
    想定場面
    1. データの取得を試みる
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. データの取得を試みる
    2. 該当データを登録
    3. 該当データを更新
    4. 該当データを削除
*/
