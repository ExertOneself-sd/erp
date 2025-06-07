package com.erp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.dto.SellResult;
import com.erp.pojo.Order;
import com.erp.service.OrderService;
import com.erp.mapper.OrderMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_order】的数据库操作Service实现
* @createDate 2025-05-13 09:08:58
*/
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>
    implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String,Object> queryOrderListServie(Integer pageNum,Integer pageSize,Order order) {
        Page<Object> page = PageHelper.startPage(pageNum,pageSize);

        List<Order> orderList=orderMapper.queryOrderListMapper(order);
        Map<String,Object> result=new HashMap<>();
        result.put("orderList",orderList);
        result.put("total",page.getTotal());
        return result;
    }

    @Override
    public List<Map<String,Object>> querySellYearServie(){
        List<Integer> integerList=orderMapper.querySellYearMapper();
        List<Map<String,Object>> list=new ArrayList<>();
        for(Integer year:integerList){
            Map<String,Object> result=new HashMap<>();
            result.put("year",year);
            result.put("label",year+"年");
            list.add(result);
        }
        return list;
    }

    @Override
    public Map<String,Object> queryYearMonthServie(String year){
        List<SellResult> sellResults= orderMapper.countSellMonthMapper(year);
        Map<String,Object> result=new HashMap<>();
        //创建封装月份数据集合
        List<String> mths=new ArrayList();
        List<Double> mnys=new ArrayList<>();
        for(int m=1;m<=12;m++){
            mths.add(m+"月");
            mnys.add(0.0);
        }

        for(SellResult sr:sellResults){
            if (sr != null) {
                Integer mth=sr.getMth();
                mnys.set(mth-1,sr.getMny());  //如果某个月份存在数据，覆盖默认值0.0
            }
        }
        result.put("xdata",mths);
        result.put("ydata",mnys);
        return result;
    }

    @Override
    public Map<String,Object> querySellNumServie(String year){
        //查询数据库
        List<SellResult> sellResults= orderMapper.countSellNumMapper(year);
        //创建List集合封装每年12个月
        List<String> mths=new ArrayList<>();
        //创建List集合封装每个12个月的销售数量
        List<Double> nums=new ArrayList<>();

        //初始化集合
        for (int x=1;x<=12;x++){
            mths.add(x+"月");
            nums.add((double) 0);
        }

        //根据对应月份的具体的销售数量，覆盖默认数量0
        for(SellResult sr:sellResults){
            if (sr != null) {
                nums.set(sr.getMth()-1,sr.getMny());
            }
        }

        //将响应数据放入Map集合
        Map<String,Object> result=new HashMap<>();
        result.put("xdata",mths);
        result.put("ydata",nums);
        return result;
    }
}




