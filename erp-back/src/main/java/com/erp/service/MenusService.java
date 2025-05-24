package com.erp.service;

import com.erp.pojo.Menus;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.vo.MenusVo;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_menus】的数据库操作Service
* @createDate 2025-05-13 14:31:19
*/
public interface MenusService extends IService<Menus> {

    /*加载左侧导航菜单，根据当前用户加载*/
    public List<MenusVo> queryMenusListService();

    /*添加菜单节点*/
    public void saveMenusService(Menus menus);


}
