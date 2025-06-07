package com.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.erp.dto.CountResult;
import com.erp.pojo.User;
import com.erp.service.UserRoleService;
import com.erp.service.UserService;
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
}
