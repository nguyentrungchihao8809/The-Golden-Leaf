package com.example.datban.service;

import com.example.datban.dto.DatBanFullDTO;
import com.example.datban.model.DatBan;
import com.example.datban.model.GioHang;
import com.example.datban.model.HoaDon;
import com.example.datban.repository.DatBanRepository;
import com.example.datban.repository.GioHangRepository;
import com.example.datban.repository.HoaDonRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DatBanFullService {

    @Autowired
    private DatBanRepository datBanRepo;

    @Autowired
    private GioHangRepository gioHangRepo;

    @Autowired
    private HoaDonRepository hoaDonRepo;

    public List<DatBanFullDTO> getDanhSachDayDu() {

        List<Long> ids = hoaDonRepo.findIdDatCoDayDu();
        List<DatBanFullDTO> result = new ArrayList<>();

        for (Long idDat : ids) {

            DatBan d = datBanRepo.findById(idDat).orElse(null);
            HoaDon h = hoaDonRepo.findByIdDat(idDat);
            List<GioHang> gList = gioHangRepo.findByIdDat(idDat);

            result.add(new DatBanFullDTO(d, h, gList));
        }

        return result;
    }
}
