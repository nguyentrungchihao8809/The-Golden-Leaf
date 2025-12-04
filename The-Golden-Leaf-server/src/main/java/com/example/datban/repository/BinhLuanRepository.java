package com.example.datban.repository;

import com.example.datban.model.BinhLuan;
import com.example.datban.model.ThucDon;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BinhLuanRepository extends JpaRepository<BinhLuan, Long> {
    List<BinhLuan> findByThucDonIdThucDonOrderByCreatedAtAsc(Long thucDonId);
}
