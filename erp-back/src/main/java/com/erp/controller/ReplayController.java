package com.erp.controller;

import com.erp.pojo.Replay;
import com.erp.service.ReplayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@CrossOrigin("*")
public class ReplayController {

    @Autowired
    private ReplayService replayService;

    @PostMapping("/saveReplay")
    public Map<String,Object> saveReplay(@RequestBody Replay replay){
        Map<String,Object> result=new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            replay.setRedate(new Date());
            replay.setScore(ThreadLocalRandom.current().nextInt(1,8));
            replayService.save(replay);
            result.put("code",200);
            result.put("msg","处理投诉回复成功！");
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/listReplay")
    public Map<String,Object> listReplay(Integer id,
                                         @RequestParam(defaultValue = "1") Integer pageNum,
                                         @RequestParam(defaultValue = "6") Integer pageSize){
        return replayService.queryReplayListService(id,pageNum,pageSize);
    }
}
