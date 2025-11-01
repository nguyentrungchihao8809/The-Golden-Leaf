package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "gio_hang")
@Data
public class GioHang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Khóa chính tự tăng

    @Column(name = "ten_mon", nullable = false)
    private String tenMon;

    @Column(name = "gia", nullable = false)
    private int gia;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ngay_gio", nullable = false)
    private String ngayGio;
    
}

