package com.example.datban.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "nguoi_dung")
@Data
public class nguoidung {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "so_dien_thoai", nullable = false, unique = true, length = 15)
    private String soDienThoai;

    @Column(unique = true, length = 100)
    private String email;

    @Column(name = "mat_khau", nullable = false)
    private String matKhau;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private VaiTro vaiTro = VaiTro.KHACH_HANG;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private TrangThaiNguoiDung trangThai = TrangThaiNguoiDung.HOAT_DONG;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    public enum VaiTro {
        KHACH_HANG, QUAN_LY, NHAN_VIEN
    }

    public enum TrangThaiNguoiDung {
        HOAT_DONG, BI_KHOA, CHO_XAC_MINH
    }
}
