package com.example.datban.dto;

import com.example.datban.model.GioHang;
import com.example.datban.model.HoaDon;
import com.example.datban.model.DatBan;

import java.util.List;

public class DatBanFullDTO {

    private DatBan datBan;
    private HoaDon hoaDon;
    private List<GioHang> gioHangList;

    public DatBanFullDTO(DatBan datBan, HoaDon hoaDon, List<GioHang> gioHangList) {
        this.datBan = datBan;
        this.hoaDon = hoaDon;
        this.gioHangList = gioHangList;
    }

    public DatBan getDatBan() {
        return datBan;
    }

    public HoaDon getHoaDon() {
        return hoaDon;
    }

    public List<GioHang> getGioHangList() {
        return gioHangList;
    }
}
