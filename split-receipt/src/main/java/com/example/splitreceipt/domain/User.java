package com.example.splitreceipt.domain;

import com.example.splitreceipt.dto.UserCreateDto;

import javax.persistence.*;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name")
    private String name;

    public User(Integer userId) {
        this.id = userId;
    }

    public User(UserCreateDto userCreateDto) {
        this.name = userCreateDto.getName();
    }

    public User(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
