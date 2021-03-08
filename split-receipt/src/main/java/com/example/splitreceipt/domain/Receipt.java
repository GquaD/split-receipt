package com.example.splitreceipt.domain;

import com.example.splitreceipt.dto.ReceiptCreateDto;

import javax.persistence.*;

/**
 * Created by Behzod Workstation on 07, March, 2021.
 */
@Entity
@Table(name = "receipt")
public class Receipt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_active")
    private Boolean isActive;

    public Receipt(Integer receiptId) {
        this.id = receiptId;
    }

    public Receipt(ReceiptCreateDto receiptCreateDto) {
        this.comment = receiptCreateDto.getComment();
        this.isActive = true;
    }

    public Receipt(){}

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

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }
}
