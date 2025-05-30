package com.erp.controller;


import com.erp.pojo.BuyList;
import com.erp.pojo.InStore;
import com.erp.service.InStoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
