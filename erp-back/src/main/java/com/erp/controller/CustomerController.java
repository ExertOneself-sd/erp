package com.erp.controller;

import com.erp.dto.CountResult;
import com.erp.pojo.Customer;
import com.erp.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/saveCust")
    public Map<String,Object> saveCustomer(@RequestBody Customer customer){
        System.out.println(customer);
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            customerService.save(customer);
            result.put("code",200);
            result.put("msg","客户信息录入成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/listCust")
    public Map<String,Object> listCustomer(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "6") Integer pageSize){
        return customerService.queryCustListService(pageNum,pageSize);
    }

    @PostMapping("/updatecCust")
    public Map<String,Object> updateCustomer(@RequestBody Customer customer){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            customerService.updateById(customer);
            result.put("code",200);
            result.put("msg","客户信息修改成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/delCust")
    public Map<String,Object> delCust(Integer id){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            customerService.removeById(id);
            result.put("code",200);
            result.put("msg","客户信息删除成功！");
        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    @GetMapping("/listAllCust")
    public List<Customer> listAllCust(){

        return customerService.queryCustIdNameService();
    }

    /*处理客户地区分布统计请求*/
    @GetMapping("/countCust")
    public List<CountResult> countCust(){
        return customerService.countCustService();
    }
}
