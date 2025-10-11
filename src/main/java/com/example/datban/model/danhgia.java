package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "danh_gia")
@Data
public class danhgia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "dat_ban_id", nullable = false)
    private datban datBan;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private nguoidung nguoiDung;

    @Column(name = "diem_danh_gia", nullable = false)
    private int diemDanhGia;

    @Column(columnDefinition = "TEXT")
    private String binhLuan;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();
}
