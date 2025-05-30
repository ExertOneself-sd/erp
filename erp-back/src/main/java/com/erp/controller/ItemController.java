package com.erp.controller;


import com.erp.pojo.Item;
import com.erp.service.ItemService;
import com.erp.util.CodeUtils;
import com.erp.vo.ItemCond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class ItemController {

    @GetMapping("/getCode")
    public String toItemCode(){
        return CodeUtils.toItemCode();
    }

    @Autowired
    private ItemService itemService;

    @PostMapping("/addItem")
    public Map<String,Object> addItem(@RequestBody Item item){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try {
            itemService.save(item);
            result.put("code",200);
            result.put("msg","商品信息添加成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping("/itemList")
    public Map<String,Object> itemList(@RequestBody ItemCond itemCond){
        return itemService.queryItemListService(itemCond);
    }

    /*处理商品的下架请求*/
    @GetMapping("/upItem/{id}")
    public Map<String,Object> upItem(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            Item item=new Item();
            item.setId(id);
            item.setStatue(0);
            itemService.updateById(item);
            result.put("code",200);
            result.put("msg","商品上架成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理商品的下架请求*/
    @GetMapping("/downItem/{id}")
    public Map<String,Object> downItem(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            Item item=new Item();
            item.setId(id);
            item.setStatue(1);
            itemService.updateById(item);
            result.put("code",200);
            result.put("msg","商品下架成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理商品的删除请求*/
    @GetMapping("/delItem/{id}")
    public Map<String,Object> delItem(@PathVariable Integer id){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            Item item=new Item();
            item.setId(id);
            item.setStatue(2);
            itemService.updateById(item);
            result.put("code",200);
            result.put("msg","商品删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @PostMapping("/updateItem")
    public Map<String,Object> updateItem(@RequestBody Item item){
        Map<String,Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            itemService.updateById(item);
            result.put("code",200);
            result.put("msg","商品信息修改成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }
}
