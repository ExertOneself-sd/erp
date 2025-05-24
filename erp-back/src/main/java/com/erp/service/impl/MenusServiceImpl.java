package com.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.pojo.Menus;
import com.erp.service.MenusService;
import com.erp.mapper.MenusMapper;
import com.erp.vo.MenusVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
* @author hzr
* @description 针对表【t_menus】的数据库操作Service实现
* @createDate 2025-05-13 14:31:18
*/
@Service
public class MenusServiceImpl extends ServiceImpl<MenusMapper, Menus>
    implements MenusService{
    @Autowired
    private MenusMapper menusMapper;
    @Override
    public List<MenusVo> queryMenusListService() {
        List<Menus> menus=menusMapper.selectList(null);
        return doListMenus(menus,0);
    }

    private List<MenusVo> doListMenus(List<Menus> menus, Integer parentId) {
        List<MenusVo> result = new ArrayList<>();
        for (Menus menu : menus) {
            if (menu.getPid() != null && menu.getPid().equals(parentId)) {
                MenusVo menusVo = new MenusVo();
                BeanUtils.copyProperties(menu, menusVo);

                menusVo.setSubMenus(doListMenus(menus, menu.getId()));
                result.add(menusVo);
            }
        }
        return result;
    }

    @Override
    public void saveMenusService(Menus menus) {

        QueryWrapper<Menus> wrapper = new QueryWrapper<>();
        wrapper.select("max(component) maxv");
        Menus ms= menusMapper.selectOne(wrapper);

        menus.setComponent(ms.getMaxv()+1);
        menusMapper.insert(menus);
    }

}




