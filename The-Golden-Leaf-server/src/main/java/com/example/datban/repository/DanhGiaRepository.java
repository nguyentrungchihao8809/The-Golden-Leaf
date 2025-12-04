package com.example.datban.repository;

import com.example.datban.model.DanhGia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DanhGiaRepository extends JpaRepository<DanhGia, Long> {

      @Query("select d from DanhGia d where d.thucDon.idThucDon = :thucDonId order by d.createdAt asc")
    List<DanhGia> findByThucDonId(@Param("thucDonId") Long thucDonId);

    @Query("select d from DanhGia d where d.thucDon.idThucDon = :thucDonId and d.userEmail = :userEmail")
    DanhGia findByThucDonIdAndUserEmail(@Param("thucDonId") Long thucDonId, @Param("userEmail") String userEmail);
    
}
