package com.erp.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.erp.pojo.Brand;
import com.erp.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
public class BrandController {

    @Autowired
    private BrandService brandService;

    @Cacheable(cacheNames = "brand_cache",key = "#root.methodName")
    @GetMapping("/brandList")
    public List<Brand> queryBrandList(){
        QueryWrapper<Brand> wrapper=new QueryWrapper<>();
        wrapper.select("brand_id","brand_name");
        return brandService.list(wrapper);
    }
}
