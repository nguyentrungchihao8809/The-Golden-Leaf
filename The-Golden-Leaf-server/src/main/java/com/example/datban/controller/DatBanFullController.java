package com.example.datban.controller;

import com.example.datban.service.DatBanFullService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/nhahang")
public class DatBanFullController {

    @Autowired
    private DatBanFullService datBanFullService;

    @GetMapping("/danh-sach-dat-ban")
    public String viewDanhSach(Model model) {

        model.addAttribute("items", datBanFullService.getDanhSachDayDu());

        return "nhahang/danh-sach-dat-ban";
    }
}
