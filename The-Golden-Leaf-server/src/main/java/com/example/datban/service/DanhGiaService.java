package com.example.datban.service;

import com.example.datban.model.DanhGia;
import com.example.datban.model.ThucDon;
import com.example.datban.repository.DanhGiaRepository;
import com.example.datban.repository.ThucDonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;



import java.util.List;

@Service
public class DanhGiaService {
    @Autowired
    private DanhGiaRepository repo;

    @Autowired
    private ThucDonRepository thucDonRepo;

    public List<DanhGia> getByThucDon(Long id) {
        return repo.findByThucDonId(id);
    }

    @Transactional
public DanhGia addDanhGia(Long thucDonId, String userEmail, int soSao) {
    ThucDon thucDon = thucDonRepo.findById(thucDonId)
            .orElseThrow(() -> new RuntimeException("Không tìm thấy món ăn"));

    // Kiểm tra xem user đã đánh giá món này chưa
    DanhGia existing = repo.findByThucDonIdAndUserEmail(thucDonId, userEmail);

    if (existing != null) {
        existing.setSoSao(soSao); // Cập nhật số sao
        return repo.save(existing);
    } else {
        DanhGia dg = new DanhGia();
        dg.setThucDon(thucDon);
        dg.setUserEmail(userEmail);
        dg.setSoSao(soSao);
        return repo.save(dg);
    }
}

}
