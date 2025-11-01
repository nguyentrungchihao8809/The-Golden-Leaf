package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "thuc_don")
@Data
public class ThucDon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idThucDon; // Khóa chính tự tăng

    @Column(name = "gia", nullable = false)
    private int gia;

    @Column(name = "anh", nullable = false)
    private String anh;

    @Column(name = "mo_ta", nullable = false)
    private int moTa;
}

