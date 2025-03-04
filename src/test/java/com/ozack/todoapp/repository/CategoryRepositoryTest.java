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

import com.ozack.todoapp.repository.entity.Category;

/* CategoryRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class CategoryRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    CategoryRepository categoryRepository;

    /* 1. 全ての Category データの取得を試みる. */
    @Test
    @Sql("/db/migration/repository/category/read.sql")
    public void test_findAll() {

        List<Category> expected = new ArrayList<>();
        expected.add(new Category(1L, "category-name-1"));
        expected.add(new Category(2L, "category-name-2"));

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<Category> actual = categoryRepository.findAll();
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
    }

    /* 2. データの登録を試みる */
    @Test
    public void test_insert() {

        Category expected = new Category(
            null,
            "category-name-1"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Category savedEntity = categoryRepository.save(expected);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        Category actual = categoryRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    /* 3. データの更新を試みる */
    @Test
    @Sql("/db/migration/repository/category/update.sql")
    public void test_update() {

        Category expected = new Category(
            1L,
            "category-name-2"
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Category savedEntity = categoryRepository.save(expected);
        logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));

        Category actual = categoryRepository.findById(savedEntity.getId()).orElse(null);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());
    }

    /* 4. データの削除を試みる */
    @Test
    @Sql("/db/migration/repository/category/update.sql")
    public void test_deleteById() {

        Long id = 1L;

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        categoryRepository.deleteById(id);
        logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));

        Category actual = categoryRepository.findById(id).orElse(null);
        assertEquals(null, actual);
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
