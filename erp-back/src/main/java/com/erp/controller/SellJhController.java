package com.erp.controller;

import com.erp.pojo.Customer;
import com.erp.pojo.SellJh;
import com.erp.service.SellJhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class SellJhController {
    @Autowired
    private SellJhService sellJhService;

    @PostMapping("/saveSell")
    public Map<String, Object> saveSell(@RequestBody SellJh sellJh) {
        System.out.println(sellJh);
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            sellJhService.save(sellJh);
            result.put("code",200);
            result.put("msg","销售过程录入成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/sellJhList")
    public Map<String, Object> sellJhList(@RequestParam(defaultValue = "1") Integer pageNum,
                                          @RequestParam(defaultValue = "6") Integer pageSize) {
        return sellJhService.querySellJhListService(pageNum,pageSize);
    }

    @PostMapping("/updateSellJh")
    public Map<String,Object> updateSellJh(@RequestBody SellJh sellJh) {
        System.out.println(sellJh);
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            sellJhService.updateById(sellJh);
            result.put("code",200);
            result.put("msg","更新客户销售过程数据成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/delSellJh")
    public Map<String,Object> delSellJh(Integer id) {
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            sellJhService.removeById(id);
            result.put("code",200);
            result.put("msg","销售过程删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
