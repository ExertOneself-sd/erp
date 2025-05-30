package com.erp.service;

import com.erp.pojo.BuyList;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.util.Map;

/**
* @author hzr
* @description 针对表【buy_list(采购单)】的数据库操作Service
* @createDate 2025-05-29 11:12:43
*/
public interface BuyListService extends IService<BuyList> {

    /*处理采购单需要自动带入*/
    public Map<String, Object> queryAutoDataBuyService(Integer id);

    /*实现采购单分页查询*/
    public Map<String, Object> queryBuyListService(Integer pageNum, Integer pageSize);

    /*实现采购单数据导出到excel*/
    public XSSFWorkbook exportExcelService();

}
