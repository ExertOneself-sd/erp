package com.erp.mapper;

import com.erp.pojo.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
* @author hzr
* @description 针对表【t_user】的数据库操作Mapper
* @createDate 2025-05-19 14:32:14
* @Entity com.erp.pojo.User
*/
public interface UserMapper extends BaseMapper<User> {

    /*保存用户信息*/
    public void saveUserMapper(User user);

}




