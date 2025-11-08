package com.example.datban.repository;

import com.example.datban.model.GioHang;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GioHangRepository extends JpaRepository<GioHang, Long> {
}
