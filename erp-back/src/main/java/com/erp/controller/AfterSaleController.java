package com.erp.controller;

import com.erp.dto.CountResult;
import com.erp.pojo.AfterSales;
import com.erp.service.AfterSalesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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

    @GetMapping("/countQuestionType")
    public List<CountResult> countQuestionType(){
        return afterSalesService.countQuestionTypeService();
    }

    @GetMapping("/countQuestionState")
    public List<CountResult> countQuestionState(){
        return afterSalesService.countQuestionStateService();
    }
}
