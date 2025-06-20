package com.erp.mapper;

import com.erp.dto.CountResult;

import com.erp.pojo.Menus;
import com.erp.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2025-05-19 14:32:14
* @Entity com.erp.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    /*保存用户信息*/
    public void saveUserMapper(User user);

    /*实现员工按照年龄段分布统计*/
    public List<CountResult> countEmployeeAgeMapper();

    /*实现员工按照学历分布统计*/
    public List<CountResult> countEmployeeEduMapper();

    /*加载当前登录用户菜单信息*/
    public List<Menus> queryUserMenusMapper(Integer uid);


}




