package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "thanh_toan")
@Data
public class ThanhToan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idThanhToan; // Khóa chính tự tăng

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ngay_gio", nullable = false)
    private String ngayGio;

    @Column(name = "trang_thai", nullable = false)
    private String status;
}

