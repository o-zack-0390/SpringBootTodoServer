package com.ozack.todoapp.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import com.ozack.todoapp.dto.response.ResponseTodoCategoryDto;
import com.ozack.todoapp.dto.response.ResponseTodoDto;
import com.ozack.todoapp.repository.entity.Category;
import com.ozack.todoapp.repository.entity.Todo;

/* TodoRepository のテストコード */
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class TodoRepositoryTest {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private TodoRepository todoRepository;

    /* 1. 指定の todo_id を保持する全ての Todo データの取得を試みる. */
    @Test
    @Sql({"/db/migration/repository/todo/common.sql", "/db/migration/repository/todo/read.sql"})
    public void test_findAllByUserIdWithCategories() {

        ResponseTodoDto data1 = new ResponseTodoDto(
            1L,
            "todo-title-1",
            true,
            Arrays.asList(
                new ResponseTodoCategoryDto(
                    1L,
                    new Category(1L, "category-name-1")
                ),
                new ResponseTodoCategoryDto(
                    2L,
                    new Category(2L, "category-name-2")
                )
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
        List<Todo> todos = todoRepository.findAllByUserIdWithCategories(1L);
        logger.info("Elapsed read time -->" + (System.currentTimeMillis() - start));

        // Dto に変換
        List<ResponseTodoDto> actual = convertResponseTodoDto(todos);

        // 値を検証
        ResponseTodoDto expected1 = expected.get(1);
        ResponseTodoDto expected2 = expected.get(0);
        ResponseTodoDto actual1 = actual.get(0);
        ResponseTodoDto actual2 = actual.get(1);
        assertEquals(expected1.id(), actual1.id());
        assertEquals(expected1.title(), actual1.title());
        assertEquals(expected1.isCheck(), actual1.isCheck());
        assertEquals(0, actual1.todoCategories().size());
        assertEquals(expected2.id(), actual2.id());
        assertEquals(expected2.title(), actual2.title());
        assertEquals(expected2.isCheck(), actual2.isCheck());
        assertEquals(
            expected2.todoCategories().get(0).category().getId(),
            actual2.todoCategories().get(0).category().getId()
        );
        assertEquals(
            expected2.todoCategories().get(0).category().getName(),
            actual2.todoCategories().get(0).category().getName()
        );
        assertEquals(
            expected2.todoCategories().get(1).category().getId(),
            actual2.todoCategories().get(1).category().getId()
        );
        assertEquals(
            expected2.todoCategories().get(1).category().getName(),
            actual2.todoCategories().get(1).category().getName()
        );
    }

    /* 2. データの登録を試みる */
    @Test
    @Sql("/db/migration/repository/todo/common.sql")
    public void test_insert() {

        Todo expected = new Todo(
            null,
            1L,
            "todo-title-1",
            true
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Todo savedEntity = todoRepository.save(expected);
        logger.info("Elapsed insert time -->" + (System.currentTimeMillis() - start));

        Todo actual = todoRepository.findById(savedEntity.getId()).orElse(null);
        assertNotNull(actual.getId());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getIsCheck(), actual.getIsCheck());
    }

    /* 3. データの更新を試みる */
    @Test
    @Sql({"/db/migration/repository/todo/common.sql", "/db/migration/repository/todo/update.sql"})
    public void test_update() {

        Todo expected = new Todo(
            1L,
            1L,
            "todo-title-2",
            false
        );

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        Todo savedEntity = todoRepository.save(expected);
        logger.info("Elapsed update time -->" + (System.currentTimeMillis() - start));

        Todo actual = todoRepository.findById(savedEntity.getId()).orElse(null);
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUserId(), actual.getUserId());
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getIsCheck(), actual.getIsCheck());
    }

    /* 4. データの削除を試みる */
    @Test
    @Sql({"/db/migration/repository/todo/common.sql", "/db/migration/repository/todo/delete.sql"})
    public void test_deleteById() {

        Long id = 1L;

        // レスポンスタイムを計測
        long start = System.currentTimeMillis();
        todoRepository.deleteById(id);
        logger.info("Elapsed delete time -->" + (System.currentTimeMillis() - start));

        Todo actual = todoRepository.findById(id).orElse(null);
        assertEquals(null, actual);
    }

    public List<ResponseTodoDto> convertResponseTodoDto(List<Todo> todos) {
        return todos.stream()
                    .map(todo -> new ResponseTodoDto(
                            todo.getId(),
                            todo.getTitle(),
                            todo.getIsCheck(),
                            todo.getTodoCategories()
                                .stream()
                                .sorted(Comparator.comparing(todoCategory -> todoCategory.getCategory().getName())) // 名前順にソート
                                .map(todoCategory -> new ResponseTodoCategoryDto( // カテゴリー型に変換
                                    todoCategory.getId(),
                                    new Category(
                                        todoCategory.getCategoryId(),
                                        todoCategory.getCategory().getName()
                                    )
                                ))
                                .collect(Collectors.toList())
                    ))
                    .collect(Collectors.toList());
    }

}

/*
    想定場面
    1. 指定の todo_id を保持する全ての Todo データの取得を試みる.
    2. データの登録を試みる.
    3. データの更新を試みる.
    4. データの削除を試みる.

    期待処理
    1. 該当する Todo データを全て取得.
    2. 該当データを登録
    4. 該当データを更新
    3. 該当データを削除
*/
