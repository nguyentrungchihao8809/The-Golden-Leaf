package com.example.datban.repository;

import com.example.datban.model.DatBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatBanRepository extends JpaRepository<DatBan, Long> {
}
