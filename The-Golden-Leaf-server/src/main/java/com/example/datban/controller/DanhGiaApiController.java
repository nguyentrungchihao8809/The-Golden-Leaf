package com.example.datban.controller;

import com.example.datban.model.DanhGia;
import com.example.datban.service.DanhGiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/danhgia")
public class DanhGiaApiController {

    @Autowired
    private DanhGiaService danhGiaService;

    // Lấy tất cả đánh giá của món ăn
    @GetMapping("/{thucDonId}")
    public List<DanhGia> getDanhGiaByThucDon(@PathVariable Long thucDonId) {
        return danhGiaService.getByThucDon(thucDonId);
    }

    // Thêm đánh giá mới
    @PostMapping("/{thucDonId}")
    public DanhGia addDanhGia(
            @PathVariable Long thucDonId,
            @RequestParam String userEmail,
            @RequestParam int soSao
    ) {
        // soSao nên validate từ 1–5
        if (soSao < 1 || soSao > 5) {
            throw new IllegalArgumentException("Số sao phải từ 1 đến 5");
        }
        return danhGiaService.addDanhGia(thucDonId, userEmail, soSao);
    }
}
