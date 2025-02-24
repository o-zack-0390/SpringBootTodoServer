package com.ozack.todoapp.repository.entity;

import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Tolerate;

/* Todo 情報 */
@Entity
@Table(name = "todo")
@RequiredArgsConstructor
@Getter
public class Todo {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "user_id")
    private final Long userId;

    @Column(name = "title")
    private final String title;

    @Column(name = "is_check")
    private final Boolean isCheck;

    @OneToMany(
        targetEntity = TodoCategory.class,
        mappedBy = "todo",
        fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<TodoCategory> todoCategories;

    @Tolerate
    public Todo(){
        this.id = null;
        this.userId = null;
        this.title = null;
        this.isCheck = null;
    }

}
