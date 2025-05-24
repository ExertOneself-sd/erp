package com.erp.service;

import com.erp.pojo.Replay;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
* @author hzr
* @description 针对表【t_replay】的数据库操作Service
* @createDate 2025-05-19 11:45:45
*/
public interface ReplayService extends IService<Replay> {
    public Map<String,Object> queryReplayListService(Integer id, Integer pageNum, Integer pageSize);
}
