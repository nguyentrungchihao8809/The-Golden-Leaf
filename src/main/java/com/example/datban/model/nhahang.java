package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalTime;

@Entity
@Table(name = "nha_hang")
@Data
public class nhahang {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ten_nha_hang", nullable = false, length = 100)
    private String tenNhaHang;

    @Column(nullable = false)
    private String diaChi;

    @Column(name = "so_dien_thoai", length = 15)
    private String soDienThoai;

    @Column(name = "gio_mo_cua")
    private LocalTime gioMoCua;

    @Column(name = "gio_dong_cua")
    private LocalTime gioDongCua;

    @Column(columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "anh_dai_dien")
    private String anhDaiDien;

    // Chủ nhà hàng
    @ManyToOne
    @JoinColumn(name = "chu_so_huu_id")
    private nguoidung chuSoHuu;
}
