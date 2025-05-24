package com.erp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.pojo.Replay;
import com.erp.service.ReplayService;
import com.erp.mapper.ReplayMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【t_replay】的数据库操作Service实现
* @createDate 2025-05-19 11:45:45
*/
@Service
public class ReplayServiceImpl extends ServiceImpl<ReplayMapper, Replay>
    implements ReplayService{

    @Autowired
    private ReplayMapper  replayMapper;
    @Override
    public Map<String, Object> queryReplayListService(Integer id, Integer pageNum, Integer pageSize) {

        // 参数校验
        if (pageNum == null || pageNum <= 0) {
            pageNum = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = 10;
        }

        // 创建分页对象
        Page<Replay> page = new Page<>(pageNum, pageSize);
        QueryWrapper<Replay> wrapper = new QueryWrapper<>();

        // 只在id不为空时添加查询条件
        if (id != null) {
            wrapper.eq("ques_id", id);
        }

        // 使用 selectPage 方法进行分页查询
        Page<Replay> replayList = replayMapper.selectPage(page, wrapper);

        Map<String, Object> result = new HashMap<>();
        result.put("total", replayList.getTotal());  // 获取总记录数
        result.put("replayList", replayList.getRecords());  // 获取当前页数据

        return result;
    }

}




