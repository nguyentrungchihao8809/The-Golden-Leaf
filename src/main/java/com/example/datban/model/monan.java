package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "mon_an")
@Data
public class monan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nha_hang_id", nullable = false)
    private nhahang nhaHang;

    @Column(name = "ten_mon", nullable = false, length = 100)
    private String tenMon;

    @Column(nullable = false)
    private Double gia;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "anh_mon")
    private String anhMon;

    @Column(name = "con_phuc_vu")
    private Boolean conPhucVu = true;
}
