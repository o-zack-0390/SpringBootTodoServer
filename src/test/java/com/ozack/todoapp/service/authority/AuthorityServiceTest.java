package com.ozack.todoapp.service.authority;

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

import com.ozack.todoapp.exception.TodoAppException;
import com.ozack.todoapp.repository.AuthorityRepository;
import com.ozack.todoapp.repository.entity.Authority;

/* AuthorityService のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class AuthorityServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    AuthorityService authorityService;

    @Autowired
    AuthorityRepository authorityRepository;

    /* 1. 全ての Authority データの取得を試みる. */
    @Test
    @Sql("/db/migration/service/authority/read.sql")
    public void test_selectAllAuthorities() {

        List<Authority> expected = new ArrayList<>();
        expected.add(new Authority(1L, "authority-name-1"));
        expected.add(new Authority(2L, "authority-name-2"));

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<Authority> actual = authorityService.selectAllAuthorities();
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        assertEquals(expected.get(0).getId(), actual.get(0).getId());
        assertEquals(expected.get(0).getName(), actual.get(0).getName());
        assertEquals(expected.get(1).getId(), actual.get(1).getId());
        assertEquals(expected.get(1).getName(), actual.get(1).getName());
    }

    /* 2. データの登録を試みる */
    @Test
    public void test_insertAuthority() {

        Authority expected = new Authority(
            null,
            "authority-name-1"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Authority savedEntity = authorityService.insertAuthority(expected);
            Authority actual = authorityRepository.findById(savedEntity.getId()).orElse(null);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.getId());
            assertEquals(expected.getName(), actual.getName());
        } catch (TodoAppException e) {
            fail(e.getMessage());
        }

    }

    /* 3. データの更新を試みる */
    @Test
    @Sql("/db/migration/service/authority/update.sql")
    public void test_updateAuthority() {

        Authority expected = new Authority(
            1L,
            "authority-name-2"
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Authority savedEntity = authorityService.updateAuthority(expected);
            Authority actual = authorityRepository.findById(savedEntity.getId()).orElse(null);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.getId());
            assertEquals(expected.getName(), actual.getName());
        } catch (TodoAppException e) {
            fail(e.getMessage());
        }

    }

    /* 4. データの削除を試みる */
    @Test
    @Sql("/db/migration/service/authority/delete.sql")
    public void test_deleteAuthority() {

        Long id = 1L;

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            authorityService.deleteAuthority(id);
            logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            Authority actual = authorityRepository.findById(id).orElse(null);
            assertEquals(null, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }

    }

}
