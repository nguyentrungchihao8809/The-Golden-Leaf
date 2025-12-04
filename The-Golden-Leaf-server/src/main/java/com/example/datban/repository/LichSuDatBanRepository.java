package com.example.datban.repository;

import com.example.datban.model.LichSuDatBan;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LichSuDatBanRepository extends JpaRepository<LichSuDatBan, Long> {
    List<LichSuDatBan> findByIdDat(Long idDat);
    List<LichSuDatBan> findByIdDatIn(List<Long> idDatList);
}
