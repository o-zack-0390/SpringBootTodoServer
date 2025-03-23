package com.ozack.todoapp.service.category;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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

import com.ozack.todoapp.repository.CategoryRepository;
import com.ozack.todoapp.repository.entity.Category;

/* CategoryService のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategoryServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    /* 1. 全ての Category データの取得を試みる. */
    @Test
    @Sql("/db/migration/service/category/read.sql")
    public void test_selectAllCategories() {

        List<Category> expected = new ArrayList<>();
        expected.add(new Category(1L, "category-name-1"));
        expected.add(new Category(2L, "category-name-2"));

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<Category> actual = categoryService.selectAllCategories();
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
    }

    /* 2. データの登録を試みる */
    @Test
    public void test_insertCategory() {

        Category expected = new Category(
            null,
            "category-name-1"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Category savedEntity = categoryService.insertCategory(expected);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            Category actual = categoryRepository.findById(savedEntity.getId()).orElse(null);
            assertNotNull(actual.getId());
            assertEquals(expected.getName(), actual.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 3. データの更新を試みる */
    @Test
    @Sql("/db/migration/service/category/update.sql")
    public void test_updateCategory() {

        Category expected = new Category(
            1L,
            "category-name-2"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Category savedEntity = categoryService.updateCategory(expected);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            Category actual = categoryRepository.findById(savedEntity.getId()).orElse(null);
            assertEquals(expected.getId(), actual.getId());
            assertEquals(expected.getName(), actual.getName());
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

    /* 4. データの削除を試みる */
    @Test
    @Sql("/db/migration/service/category/delete.sql")
    public void test_deleteCategory() {

        Long id = 1L;

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            categoryService.deleteCategory(id);
            logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            Category actual = categoryRepository.findById(id).orElse(null);
            assertEquals(null, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}

/*
    想定場面
    1. 全ての Category データの取得を試みる.
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. Category データを全て取得.
    2. 該当データを登録
    4. 該当データを更新
    3. 該当データを削除
*/
