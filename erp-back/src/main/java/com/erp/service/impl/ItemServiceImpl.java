package com.erp.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.pojo.Item;
import com.erp.service.ItemService;
import com.erp.mapper.ItemMapper;
import org.springframework.stereotype.Service;

/**
* @author hzr
* @description 针对表【t_item】的数据库操作Service实现
* @createDate 2025-05-13 09:08:58
*/
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item>
    implements ItemService{

}




