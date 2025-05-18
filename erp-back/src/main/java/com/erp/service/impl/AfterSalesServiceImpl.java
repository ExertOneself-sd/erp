package com.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.pojo.AfterSales;
import com.erp.service.AfterSalesService;
import com.erp.mapper.AfterSalesMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_after_sales】的数据库操作Service实现
* @createDate 2025-05-13 09:08:58
*/
@Service
public class AfterSalesServiceImpl extends ServiceImpl<AfterSalesMapper, AfterSales>
    implements AfterSalesService{

    @Autowired
    private AfterSalesMapper afterSalesMapper;

    @Override
    public Map<String,Object> queryAfterSaleListService(AfterSales afterSales){
        Page<Object> page=PageHelper.startPage(afterSales.getPageNum(),afterSales.getPageSize());

        List<AfterSales> afterSalesList=afterSalesMapper.queryAfterSaleMapper(afterSales);
        Map<String,Object> result=new HashMap<>();
        result.put("afterSalesList",afterSalesList);
        result.put("total", page.getTotal());
        return result;
    }

}




