package com.example.datban.service.impl;

import com.example.datban.model.DatBan;
import com.example.datban.repository.DatBanRepository;
import com.example.datban.service.DatBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DatBanServiceImpl implements DatBanService {

    @Autowired
    private DatBanRepository datBanRepository;

   
    @Override
public DatBan saveDatBan(DatBan datBan) {
    try {
        return datBanRepository.save(datBan);
    } catch (Exception e) {
        e.printStackTrace(); // in ra lỗi chi tiết
        throw e;
    }
}

}

