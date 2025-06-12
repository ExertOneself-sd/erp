package com.erp.controller;

import com.erp.pojo.Menus;
import com.erp.service.MenusService;
import com.erp.vo.MenusVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@CrossOrigin
public class MenusController {

    @Autowired
    private MenusService menusService;


    @RequestMapping("/listMenus")
    public List<MenusVo> listMenus() {

        return menusService.queryMenusListService();
    }

    @RequestMapping("/compIndex")
    public Integer compIndex(Integer id) {
        Menus menus = menusService.getById(id);
        return menus.getComponent();
    }

    /*处理菜单节点信息的添加请求*/
    @PostMapping("/saveMenus")
    public Map<String,Object> saveMenus(@RequestBody Menus menus){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            menusService.saveMenusService(menus);
            result.put("code",200);
            result.put("msg","节点信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/deleteMenus/{id}")
    public Map<String,Object> deleteMenus(@PathVariable Integer id){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            menusService.removeById(id);
            result.put("code",200);
            result.put("msg","菜单节点删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*菜单节点更新请求*/
    @PostMapping("/updateMenus")
    public Map<String,Object> updateMenus(@RequestBody Menus menus){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            menusService.updateById(menus);
            result.put("code",200);
            result.put("msg","菜单节点信息更新成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}

