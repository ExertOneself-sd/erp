package com.erp.controller;

import com.erp.pojo.Categrory;
import com.erp.service.CategroryService;
import com.erp.vo.TreeVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategroryService categroryService;

    /*处理加载商品类目树请求*/
    @GetMapping("/categoryList")
    public List<TreeVo> categoryList(){
        return categroryService.queryCategoryListService();
    }

    /*处理商品类目添加请求*/
    @PostMapping("/saveCategory")
    public Map<String,Object> saveCategory(@RequestBody Categrory categrory){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            categroryService.save(categrory);
            result.put("code",200);
            result.put("msg","商品类目保存成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/loadCategory/{id}")
    public Categrory loadCategory(@PathVariable Integer id){
        return categroryService.getById(id);
    }

    @PostMapping("/updateCategory")
    public Map<String,Object> updateCategory(@RequestBody Categrory categrory){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            categroryService.updateById(categrory);
            result.put("code",200);
            result.put("msg","商品类目更新成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;

    }

    @GetMapping("/delCategory/{id}")
    public Map<String,Object> delCategory(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            categroryService.removeById(id);
            result.put("code",200);
            result.put("msg","商品类目删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
