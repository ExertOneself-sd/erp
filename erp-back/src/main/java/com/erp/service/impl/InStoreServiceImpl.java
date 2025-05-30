package com.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.mapper.BuyListMapper;
import com.erp.mapper.ItemMapper;
import com.erp.pojo.BuyList;
import com.erp.pojo.InStore;
import com.erp.pojo.Item;
import com.erp.service.InStoreService;
import com.erp.mapper.InStoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author hzr
* @description 针对表【t_in_store(入库单)】的数据库操作Service实现
* @createDate 2025-05-30 11:02:11
*/
@Service
public class InStoreServiceImpl extends ServiceImpl<InStoreMapper, InStore>
    implements InStoreService{

    @Autowired
    private  ItemMapper itemMapper;
    @Autowired
    private BuyListMapper buyListMapper;
    @Autowired
    private InStoreMapper inStoreMapper;


    public void saveInStoreService(BuyList buyList){
        //获得商品id
        Integer productId = buyList.getProductId();
        //通过商品id获取商品库存
        Item goods=itemMapper.selectById(productId);

        Item item=new Item();
        item.setId(productId);
        item.setStore(goods.getStore()+buyList.getFactBuyNum());

        //更新商品库存
        itemMapper.updateById(item);

        //获得采购单id
        Integer buyId=buyList.getBuyId();
        //更新采购单状态
        BuyList b1=new BuyList();
        b1.setBuyId(buyId);
        b1.setIsIn("0");
        buyListMapper.updateById(b1);

        //查询记录形成的入库单
        InStore inStore=new InStore();
        inStore.setStoreId(buyList.getStoreId());
        inStore.setProductId(buyList.getProductId());
        inStore.setInNum(buyList.getFactBuyNum());
        inStore.setCreateBy(1009);
        inStore.setCreateTime(new Date());
        inStore.setIsIn("1");
        inStoreMapper.insert(inStore);

    }

}




