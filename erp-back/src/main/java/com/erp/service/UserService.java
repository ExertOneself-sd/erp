package com.erp.service;

import com.erp.dto.CountResult;
import com.erp.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.erp.vo.MenusVo;

import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_user】的数据库操作Service
* @createDate 2025-05-19 14:32:14
*/
public interface UserService extends IService<User> {

    /*实现用户信息分页查询*/
    public Map<String,Object> queryUserListService(Integer pageNum,Integer pageSize);

    public void saveUserRolerService(User user);

    public void updateUserRolerService(User user);

    public void deleteUserRolerService(Integer id);

    /*实现员工按照年龄段分布统计*/
    public List<CountResult> countEmployeeAgeService();

    /*实现员工按照学历分布统计*/
    public List<CountResult> countEmployeeEduService();

<<<<<<< HEAD
    /*加载左侧导航菜单，根据当前用户加载*/
    public List<MenusVo> queryUserMenusListService(Integer uid);

=======
>>>>>>> 9b36866f743edc05e0f02f00103b929e0d29fc81
}
