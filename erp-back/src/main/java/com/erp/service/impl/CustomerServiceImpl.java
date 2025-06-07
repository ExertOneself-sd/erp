package com.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.dto.CountResult;
import com.erp.dto.HisData;
import com.erp.mapper.OrderMapper;
import com.erp.pojo.Customer;
import com.erp.service.CustomerService;
import com.erp.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_customer】的数据库操作Service实现
* @createDate 2025-05-13 09:08:58
*/
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer>
    implements CustomerService{

    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Transactional
    @Override
    public Map<String,Object> queryCustListService(Integer pageNum,Integer pageSize){

        List<HisData> hisDatas = orderMapper.queryCountHisDataMapper();

        // 处理历史数据，避免NullPointerException
        if (hisDatas != null) {
            for(HisData hisData:hisDatas){
                if (hisData != null && hisData.getCustId() != null) {
                    Customer cust=new Customer();
                    cust.setId(hisData.getCustId());
                    cust.setHitTotal(hisData.getHitTotal());
                    customerMapper.updateById(cust);
                }
            }
        }

        Map<String,Object> result = new HashMap<>();
        Page page = new Page(pageNum, pageSize);
        System.out.println(page.getTotal());
        List list = customerMapper.selectList(page, null);
        result.put("custList", list);
        result.put("total",page.getTotal());
        return result;
    }

    @Override
    public List<Customer> queryCustIdNameService(){
        QueryWrapper<Customer> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id","cust_name");
        List<Customer> customerlist = customerMapper.selectList(wrapper);
        return customerlist;
    }

    @Override
    public List<CountResult> countCustService(){
        return customerMapper.countCustomerAreaMapper();
    }
}




