package com.example.datban.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "danh_gia")
public class DanhGia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Liên kết đến món ăn
    @ManyToOne
    @JoinColumn(name = "thuc_don_id")
    private ThucDon thucDon;

    // Người đánh giá
    private String userEmail;

    // Số sao (1–5)
    @Column(nullable = false)
    private int soSao;

    // Thời gian tạo đánh giá
    private LocalDateTime createdAt = LocalDateTime.now();

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public ThucDon getThucDon() { return thucDon; }
    public void setThucDon(ThucDon thucDon) { this.thucDon = thucDon; }

    public String getUserEmail() { return userEmail; }
    public void setUserEmail(String userEmail) { this.userEmail = userEmail; }

    public int getSoSao() { return soSao; }
    public void setSoSao(int soSao) { this.soSao = soSao; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
