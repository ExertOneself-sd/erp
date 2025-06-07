package com.erp.controller;


import com.erp.pojo.OutStore;
import com.erp.service.OutStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class OutStoreController {

    @Autowired
    OutStoreService outStoreService;

    /*处理商品信息出库请求*/
    @PostMapping("/doItemOutStore")
    public Map<String,Object> doItemOutStore(@RequestBody OutStore outStore){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            outStoreService.saveOutStoreService(outStore);
            result.put("code",200);
            result.put("msg","商品出库成功!");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/queryOutList")
    public Map<String,Object> queryOutList(@RequestParam(defaultValue = "1") Integer pageNum,
                                           @RequestParam(defaultValue = "6") Integer pageSize){
        return outStoreService.queryOutStoreService(pageNum,pageSize);
    }

    @PostMapping("/updateOutStore/{id}")
    public Map<String,Object> updateOutStore(@PathVariable Integer id){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败!");
        try{
            OutStore outStore=new OutStore();
            outStore.setOutsId(id);
            outStore.setIsOut("1");
            outStoreService.updateById(outStore);
            result.put("code",200);
            result.put("msg","出库单确认成功!");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
