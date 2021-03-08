package com.example.splitreceipt.domain;

import com.example.splitreceipt.dto.InputCreateDto;

import javax.persistence.*;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Entity
@Table(name = "input")
public class Input {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "amount")
    private Double amount;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "receipt_id")
    private Receipt receipt;

    public Input(InputCreateDto inputCreateDto) {
        this.amount = inputCreateDto.getAmount();
        this.comment = inputCreateDto.getComment();
        this.receipt = new Receipt(inputCreateDto.getReceiptId());
        this.user = new User(inputCreateDto.getUserId());
    }
    public Input(){}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Receipt getReceipt() {
        return receipt;
    }

    public void setReceipt(Receipt receipt) {
        this.receipt = receipt;
    }
}
