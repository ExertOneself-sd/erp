package com.erp.controller;

import com.erp.pojo.AfterSales;
import com.erp.service.AfterSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin("*")
public class AfterSaleController {

    @Autowired
    private AfterSalesService afterSalesService;

    @PostMapping("/listAfterSale")
    public Map<String,Object> listAfterSales(@RequestBody AfterSales afterSales){
        return afterSalesService.queryAfterSaleListService(afterSales);
    }
}
