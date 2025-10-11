package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "dat_ban")
@Data
public class datban {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "nguoi_dung_id", nullable = false)
    private nguoidung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "nha_hang_id", nullable = false)
    private nhahang nhaHang;

    @ManyToOne
    @JoinColumn(name = "ban_an_id", nullable = false)
    private banan banAn;

    @Column(name = "thoi_gian_dat", nullable = false)
    private LocalDateTime thoiGianDat;

    @Column(name = "so_nguoi", nullable = false)
    private Integer soNguoi;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TrangThaiDat trangThai = TrangThaiDat.CHO_XAC_NHAN;

    @Column(length = 255)
    private String ghiChu;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    public enum TrangThaiDat {
        CHO_XAC_NHAN, DA_XAC_NHAN, DA_HUY, DA_HOAN_TAT
    }
}
