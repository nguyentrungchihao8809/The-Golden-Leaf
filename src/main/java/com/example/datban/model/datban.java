package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "dat_ban")
@Data
public class DatBan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idBan; // Khóa chính tự tăng

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "ngay_gio", nullable = false)
    private String ngayGio;

    @Column(name = "so_luong", nullable = false)
    private int soLuong;
}

