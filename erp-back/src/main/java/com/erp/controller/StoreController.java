package com.erp.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.erp.pojo.Store;
import com.erp.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class StoreController {

    @Autowired
    private StoreService storeService;

    @GetMapping("/storeList")
    public List<Store> queryStoreList(){
        QueryWrapper<Store> wrapper = new QueryWrapper<>();
        wrapper.select("store_id","store_name");
        List<Store> list = storeService.list(wrapper);
        return list;
    }

    @GetMapping("/storePageList")
    public Map<String, Object> queryStorePageList(@RequestParam(defaultValue = "1") Integer pageNum,
                                                  @RequestParam(defaultValue = "6") Integer pageSize){
        Page<Store> page = new Page<>(pageNum, pageSize);
        List<Store> storeList= storeService.list(page);
        Map<String, Object> result = new HashMap<>();
        result.put("total", page.getTotal());
        result.put("storeList", storeList);
        return result;
    }

    @PostMapping("/saveStore")
    public Map<String, Object> saveStore(@RequestBody Store store){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            storeService.save(store);
            result.put("code",200);
            result.put("msg","仓库信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping("/updateStore")
    public Map<String,Object> updateStore(@RequestBody Store store){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            storeService.updateById(store);
            result.put("code",200);
            result.put("msg","仓库信息修改成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/delStore/{id}")
    public Map<String,Object> delStore(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            storeService.removeById(id);
            result.put("code",200);
            result.put("msg","仓库信息删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
