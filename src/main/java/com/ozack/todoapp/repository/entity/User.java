package com.ozack.todoapp.repository.entity;

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

/* ユーザー情報 */
@Entity
@Table(name = "user")
@RequiredArgsConstructor
@Getter
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final Long id;

    @Column(name = "authority_id")
    private final Long authorityId;

    @Column(name = "name")
    private final String name;

    @Column(name = "email")
    private final String email;

    @Column(name = "password")
    private final String password;

    @ManyToOne(
        targetEntity = Authority.class,
        fetch = FetchType.LAZY)
    @JoinColumn(
        name = "authority_id",
        insertable = false,
        updatable = false)
    private Authority authority;

    @Tolerate
    public User(){
        this.id = null;
        this.authorityId = null;
        this.name = null;
        this.email = null;
        this.password = null;
    }

}
