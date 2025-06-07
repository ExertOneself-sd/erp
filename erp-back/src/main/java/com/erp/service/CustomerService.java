package com.erp.service;

import com.erp.dto.CountResult;
import com.erp.pojo.Customer;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_customer】的数据库操作Service
* @createDate 2025-05-13 09:08:58
*/
public interface CustomerService extends IService<Customer> {

    public Map<String,Object> queryCustListService(Integer pageNum, Integer pageSize);

    public List<Customer> queryCustIdNameService();

    public List<CountResult> countCustService();
}
