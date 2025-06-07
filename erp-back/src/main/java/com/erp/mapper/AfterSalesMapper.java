package com.erp.mapper;

import com.erp.dto.CountResult;
import com.erp.pojo.AfterSales;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author hzr
* @description 针对表【t_after_sales】的数据库操作Mapper
* @createDate 2025-05-13 09:08:58
* @Entity com.erp.pojo.AfterSales
*/
public interface AfterSalesMapper extends BaseMapper<AfterSales> {
    public List<AfterSales> queryAfterSaleMapper(AfterSales afterSales);

    /*按照售后投诉类型进行投诉统计*/
    public List<CountResult> countQuestionTypeMapper();

    /*按照处理状态统计*/
    public List<CountResult> countQuestionStateMapper();
}




