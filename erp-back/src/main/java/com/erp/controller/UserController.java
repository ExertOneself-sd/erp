package com.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.erp.dto.CountResult;
import com.erp.pojo.User;
import com.erp.service.UserRoleService;
import com.erp.service.UserService;
import com.erp.vo.MenusVo;
import com.erp.vo.UserVo;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/userList")
    public Map<String, Object> userList(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "6") Integer pageSize) {
        return userService.queryUserListService(pageNum, pageSize);
    }

    /*添加方法处理用户信息添加请求*/
    @PostMapping("/saveUser")
    public Map<String, Object> saveUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 400);
        result.put("msg", "操作失败！");
        try{
            userService.saveUserRolerService(user);
            result.put("code", 200);
            result.put("msg","用户信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Autowired
    private UserRoleService userRoleService;

    @GetMapping("/queryUserRids/{id}")
    public List<Integer> queryUserRids(@PathVariable Integer id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("uid",id);
        wrapper.select("rid");
        List<Integer> list=userRoleService.listObjs(wrapper);
        return list;
    }

    @PostMapping("/updateUser")
    public Map<String, Object> updateUser(@RequestBody User user) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 400);
        result.put("msg", "操作失败！");
        try{
            userService.updateUserRolerService(user);
            result.put("code", 200);
            result.put("msg","用户信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/delUser/{id}")
    public Map<String, Object> delUser(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code", 400);
        result.put("msg", "操作失败！");
        try{
            userService.deleteUserRolerService(id);
            result.put("code", 200);
            result.put("msg","用户删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/countEmpAge")
    public List<CountResult> countEmpAge() {
        return userService.countEmployeeAgeService();
    }

    @GetMapping("countEmpEdu")
    public List<CountResult> countEmpEdu() {
        return userService.countEmployeeEduService();
    }
<<<<<<< HEAD

    /*处理用户登录请求*/
    @PostMapping("/userLogin")
    public Map<String,Object> userLogin(@RequestBody UserVo user, HttpSession session) {
        Map<String,Object> result = new HashMap<>();
        result.put("code", 400);
        result.put("msg", "登录失败！");
        try{
            QueryWrapper<User> wrapper = new QueryWrapper();
            wrapper.eq("uname",user.getUname());
            List<User> list=userService.list(wrapper);
            if(list!=null&&list.size()==1){
                //获得数据库查询到的用户对象
                User dbUser=list.get(0);
                if(dbUser.getUpwd().equals(user.getUpwd())){
                    //将当前登录用户保存到session会话中
                    session.setAttribute("online",dbUser);
                    result.put("code", 200);
                    result.put("msg","登录成功！");
                }
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理加载当前登录用户菜单的请求*/
    @GetMapping("/queryUserMenus")
    public List<MenusVo> queryUserMenus(HttpSession session){
        //获得系统当前登录用户
        User user= (User) session.getAttribute("online");
        return userService.queryUserMenusListService(user.getId());
    }
=======
>>>>>>> 9b36866f743edc05e0f02f00103b929e0d29fc81
}
