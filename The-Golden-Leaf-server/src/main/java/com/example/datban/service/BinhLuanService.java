package com.example.datban.service;

import com.example.datban.model.BinhLuan;
import com.example.datban.model.ThucDon;
import com.example.datban.repository.BinhLuanRepository;
import com.example.datban.repository.ThucDonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BinhLuanService {

    private final BinhLuanRepository binhLuanRepository;
    private final ThucDonRepository thucDonRepository;

    public BinhLuanService(BinhLuanRepository binhLuanRepository, ThucDonRepository thucDonRepository) {
        this.binhLuanRepository = binhLuanRepository;
        this.thucDonRepository = thucDonRepository;
    }

    // Lấy tất cả bình luận của món ăn
    public List<BinhLuan> getBinhLuanByThucDonId(Long idThucDon) {
        return binhLuanRepository.findByThucDonIdThucDonOrderByCreatedAtAsc(idThucDon);
    }

    // Thêm bình luận
    @Transactional
    public BinhLuan addBinhLuan(Long idThucDon, String userEmail, String noiDung) {
        ThucDon thucDon = thucDonRepository.findById(idThucDon)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

        BinhLuan binhLuan = new BinhLuan();
        binhLuan.setThucDon(thucDon);
        binhLuan.setUserEmail(userEmail);
        binhLuan.setNoiDung(noiDung);

        return binhLuanRepository.save(binhLuan);
    }
}
