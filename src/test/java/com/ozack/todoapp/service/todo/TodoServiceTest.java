package com.ozack.todoapp.service.todo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
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

import com.ozack.todoapp.dto.response.ResponseTodoDto;
import com.ozack.todoapp.repository.TodoRepository;
import com.ozack.todoapp.repository.entity.Category;
import com.ozack.todoapp.repository.entity.Todo;

/* TodoService のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoServiceTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TodoService todoService;

    @Autowired
    private TodoRepository todoRepository;

    /* 1. 指定の user_id を保持する全ての Todo データの取得を試みる. */
    @Test
    @Sql({"/db/migration/service/todo/common.sql", "/db/migration/service/todo/read.sql"})
    public void test_selectAllTodosByUserIdWithCategories() {

        ResponseTodoDto data1 = new ResponseTodoDto(
            1L,
            "todo-title-1",
            true,
            Arrays.asList(
                new Category(1L, "category-name-1"),
                new Category(2L, "category-name-2")
            )
        );
        ResponseTodoDto data2 = new ResponseTodoDto(
            2L,
            "todo-title-2",
            false,
            new ArrayList<>()
        );
        List<ResponseTodoDto> expected = Arrays.asList(data1, data2);

        // データ取得時のレスポンスタイムを計測
        long start = System.currentTimeMillis();
        List<ResponseTodoDto> actual = todoService.selectAllTodosByUserIdWithCategories(1L);
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        // 値を検証
        ResponseTodoDto expected1 = expected.get(1);
        ResponseTodoDto expected2 = expected.get(0);
        ResponseTodoDto actual1 = actual.get(0);
        ResponseTodoDto actual2 = actual.get(1);
        assertEquals(expected1.id(), actual1.id());
        assertEquals(expected1.title(), actual1.title());
        assertEquals(expected1.isCheck(), actual1.isCheck());
        assertEquals(0, actual1.categories().size());
        assertEquals(expected2.id(), actual2.id());
        assertEquals(expected2.title(), actual2.title());
        assertEquals(expected2.isCheck(), actual2.isCheck());
        assertEquals(
            expected2.categories().get(0).getId(),
            actual2.categories().get(0).getId()
        );
        assertEquals(
            expected2.categories().get(0).getName(),
            actual2.categories().get(0).getName()
        );
        assertEquals(
            expected2.categories().get(1).getId(),
            actual2.categories().get(1).getId()
        );
        assertEquals(
            expected2.categories().get(1).getName(),
            actual2.categories().get(1).getName()
        );
    }

    /* 2. データの登録を試みる */
    @Test
    @Sql("/db/migration/service/todo/common.sql")
    public void test_insertTodo() {

        // 期待値
        Todo expected = new Todo(
            null,
            1L,
            "todo-title-1",
            true
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Todo actual = todoService.insertTodo(expected);
            logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.getId());
            assertEquals(expected.getUserId(), actual.getUserId());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getIsCheck(), actual.getIsCheck());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /* 3. データの更新を試みる */
    @Test
    @Sql({"/db/migration/service/todo/common.sql", "/db/migration/service/todo/update.sql"})
    public void test_updateTodo() {

        // 期待値
        Todo expected = new Todo(
            1L,
            1L,
            "todo-title-2",
            false
        );

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            Todo actual = todoService.updateTodo(expected);
            logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            assertNotNull(actual.getId());
            assertEquals(expected.getUserId(), actual.getUserId());
            assertEquals(expected.getTitle(), actual.getTitle());
            assertEquals(expected.getIsCheck(), actual.getIsCheck());
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    /* 4. データの削除を試みる */
    @Test
    @Sql({"/db/migration/service/todo/common.sql", "/db/migration/service/todo/delete.sql"})
    public void test_deleteTodo() {

        Long id = 1L;

        try {
            // レスポンスタイムを計測
            long start = System.currentTimeMillis();
            todoService.deleteTodo(id);
            logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));
            // 値を検証
            Todo actual = todoRepository.findById(id).orElse(null);
            assertEquals(null, actual);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

}

/*
    想定場面
    1. 指定の user_id を保持する全ての Todo データの取得を試みる.
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. 該当する Todo データを全て取得.
    2. 該当データを登録
    4. 該当データを更新
    3. 該当データを削除
*/
