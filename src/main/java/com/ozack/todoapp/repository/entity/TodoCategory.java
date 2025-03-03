package com.ozack.todoapp.repository.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Tolerate;

/* Todo と Category のマッピング情報 */
@Entity
@Table(name = "todo_category")
@RequiredArgsConstructor
@Getter
public class TodoCategory {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "todo_id")
    private final Long todoId;

    @Column(name = "category_id")
    private final Long categoryId;

    @ManyToOne(
        targetEntity = Todo.class,
        fetch = FetchType.LAZY)
    @JoinColumn(
        name = "todo_id",
        insertable = false,
        updatable = false)
    @JsonBackReference
    private Todo todo;

    @ManyToOne(
        targetEntity = Category.class,
        fetch = FetchType.LAZY)
    @JoinColumn(
        name = "category_id",
        insertable = false,
        updatable = false)
    private Category category;

    @Tolerate
    public TodoCategory(){
        this.id = null;
        this.todoId = null;
        this.categoryId = null;
    }

}
