package com.erp.controller;

import com.erp.pojo.Menus;
import com.erp.service.MenusService;
import com.erp.vo.MenusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MenusController {

    @Autowired
    private MenusService menusService;

    @CrossOrigin(origins = "*")
    @RequestMapping("/listMenus")
    public List<MenusVo> listMenus() {
        return menusService.queryMenusListService();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping("/compIndex")
    public Integer compIndex(Integer id) {
        Menus menus = menusService.getById(id);
        return menus.getComponent();
    }

}

