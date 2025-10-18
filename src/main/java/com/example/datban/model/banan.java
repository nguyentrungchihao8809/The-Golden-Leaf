package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ban_an")
@Data
public class banan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nha_hang_id", nullable = false)
    private nhahang nhaHang;

    @Column(name = "ten_ban", length = 50)
    private String tenBan;

    private Integer sucChua;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TrangThaiBan trangThai = TrangThaiBan.TRONG;

    public enum TrangThaiBan {
        TRONG, DA_DAT, DANG_PHUC_VU, KHONG_HOAT_DONG
    }
}
