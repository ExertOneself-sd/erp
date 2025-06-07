package com.erp.service;

import com.erp.dto.CountResult;
import com.erp.pojo.AfterSales;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_after_sales】的数据库操作Service
* @createDate 2025-05-13 09:08:58
*/
public interface AfterSalesService extends IService<AfterSales> {

    public Map<String,Object> queryAfterSaleListService(AfterSales afterSales);

    public List<CountResult> countQuestionTypeService();

    public List<CountResult> countQuestionStateService();
}
