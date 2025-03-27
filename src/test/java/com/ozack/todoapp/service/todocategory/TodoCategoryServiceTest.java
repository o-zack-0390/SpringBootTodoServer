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

import com.ozack.todoapp.dto.response.ResponseTodoCategoryDto;
import com.ozack.todoapp.repository.TodoCategoryRepository;
import com.ozack.todoapp.repository.entity.Category;
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

    /* 1. データの登録を試みる */
    @Test
    @Sql("/db/migration/service/todocategory/common.sql")
    public void test_insertTodoCategories() {

        List<ResponseTodoCategoryDto> expected = new ArrayList<>();
        expected.add(new ResponseTodoCategoryDto(null, new Category(1L, null)));
        expected.add(new ResponseTodoCategoryDto(null, new Category(2L, null)));

        List<TodoCategory> insertData = new ArrayList<>();
        insertData.add(new TodoCategory(null, 1L, 1L));
        insertData.add(new TodoCategory(null, 1L, 2L));

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            List<ResponseTodoCategoryDto> actual = todoCategoryService.insertTodoCategories(insertData);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.get(0).id());
            assertEquals(expected.get(0).category().getId(), actual.get(0).category().getId());
            assertNotNull(actual.get(1).id());
            assertEquals(expected.get(1).category().getId(), actual.get(1).category().getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 2. データの更新を試みる. */
    @Test
    @Sql({"/db/migration/service/todocategory/common.sql", "/db/migration/service/todocategory/update.sql"})
    public void test_updateTodoCategories() {

        List<ResponseTodoCategoryDto> expected = new ArrayList<>();
        expected.add(new ResponseTodoCategoryDto(1L, new Category(1L, null)));
        expected.add(new ResponseTodoCategoryDto(2L, new Category(3L, null)));

        List<TodoCategory> updateData = new ArrayList<>();
        updateData.add(new TodoCategory(1L, 1L, 1L));
        updateData.add(new TodoCategory(2L, 1L, 3L));

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            List<ResponseTodoCategoryDto> actual = todoCategoryService.updateTodoCategories(updateData);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.get(0).id());
            assertEquals(expected.get(0).category().getId(), actual.get(0).category().getId());
            assertNotNull(actual.get(1).id());
            assertEquals(expected.get(1).category().getId(), actual.get(1).category().getId());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 3. データの削除を試みる. */
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
    1. データの登録を試みる.
    2. データの更新を試みる.
    3. データの削除を試みる.

    期待処理
    1. 該当データを登録
    2. 該当データを更新
    3. 該当データを削除
*/
