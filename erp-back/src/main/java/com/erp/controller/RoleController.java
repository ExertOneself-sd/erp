package com.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.erp.pojo.RoleMenu;

import com.erp.pojo.Roler;
import com.erp.service.RoleMenuService;
import com.erp.service.RolerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin
public class RoleController {
    @Autowired
    private RolerService rolerService;

    @GetMapping("/rolerList")
    public Map<String, Object> roleList(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "6") Integer pageSize) {
        return rolerService.queryRolePageListService(pageNum, pageSize);
    }

    @PostMapping("/updateRoler")
    public Map<String, Object> updateRoler(@RequestBody Roler roler) {

        Map<String, Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            rolerService.updateById(roler);
            result.put("code",200);
            result.put("msg","更新角色信息成功！");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理角色信息添加请求*/
    @PostMapping("/saveRoler")
    public Map<String,Object> savaRoler(@RequestBody Roler roler) {
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            rolerService.save(roler);
            result.put("code",200);
            result.put("msg","角色信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @Autowired
    private RoleMenuService roleMenuService;

    @GetMapping("/delRoler")
    public Map<String,Object> delRoler(@RequestParam Integer id) {
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            rolerService.removeById(id);
            QueryWrapper<RoleMenu> cond=new QueryWrapper<>();
            cond.eq("rid",id);
            roleMenuService.remove(cond);
            result.put("code",200);
            result.put("msg","角色信息删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理角色授权请求*/
    @PostMapping("/grantRolerMenus")
    public Map<String,Object> grantRolerMenus(@RequestBody Integer[] ids) {
        System.out.println("ids="+ Arrays.toString(ids));
        Map<String,Object> result=new HashMap<>();
        List<RoleMenu> list=new ArrayList<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            Integer rid = ids[0];
            for(int x=1;x<ids.length;x++){
                //完成角色授权，给角色和菜单的中间关系添加数据，建立关系
                RoleMenu rm = new RoleMenu();
                rm.setRid(rid);
                rm.setMid(ids[x]);
                list.add(rm);
            }
            //创建对象，封装删除的where条件
            QueryWrapper<RoleMenu> cond=new QueryWrapper<>();
            cond.eq("rid",rid);
            roleMenuService.remove(cond);
            roleMenuService.saveBatch(list);
            result.put("code",200);
            result.put("msg","角色授权成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理根据角色id查询所有菜单id集合*/
    @GetMapping("/loadRoleMid/{rid}")
    public List<Integer> loadRoleMid(@PathVariable Integer rid){

        return roleMenuService.queryRoleMidsListService(rid);
    }

    /*加载所有角色信息*/
    @GetMapping("/loadAllRoles")
    public List<Roler> loadAllRoles(){
        QueryWrapper<Roler> wrapper=new QueryWrapper<>();
        wrapper.select("id","rname");
        List<Roler> list=rolerService.list(wrapper);
        return list;
    }

}
