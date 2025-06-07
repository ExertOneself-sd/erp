package com.erp.controller;


import com.erp.pojo.BuyList;
import com.erp.pojo.InStore;
import com.erp.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class InStoreController {

    @Autowired
    private InStoreService inStoreService;

    @PostMapping("/buyInStore")
    public Map<String,Object> buyInStore(@RequestBody BuyList buyList) {
        Map<String, Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            inStoreService.saveInStoreService(buyList);
            result.put("code",200);
            result.put("msg","采购商品入库成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/queryInList")
    public Map<String,Object> queryInStoreList(@RequestParam(defaultValue = "1") Integer pageNum,
                                               @RequestParam(defaultValue = "6") Integer pageSize){
        return inStoreService.queryInStoreListService(pageNum,pageSize);
    }

    @PostMapping("/updateInStore/{id}")
    public Map<String,Object> updateInStore(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            InStore inStore=new InStore();
            inStore.setInsId(id);
            inStore.setIsIn("0");
            inStoreService.updateById(inStore);
            result.put("code",200);
            result.put("msg","入库单确认成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
