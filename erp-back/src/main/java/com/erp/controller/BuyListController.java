package com.erp.controller;

import com.erp.pojo.BuyList;
import com.erp.service.BuyListService;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.UncheckedIOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin
public class BuyListController {

    @Autowired
    private BuyListService buyListService;

    /*处理采购信息需要自动带入数据的请求*/
    @GetMapping("/buyAutoInfo/{id}")
    public Map<String, Object> buyAutoInfo(@PathVariable Integer id) {
        return buyListService.queryAutoDataBuyService(id);
    }

    /*处理采购单数据保存的请求*/
    @PostMapping("/saveBuyList")
    public Map<String, Object> saveBuyList(@RequestBody BuyList buyList) {
        Map<String, Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            buyList.setFactBuyNum(0);
            buyList.setBuyTime(new Date());
            buyList.setIsIn("0");
            buyListService.save(buyList);
            result.put("code",200);
            result.put("msg","采购单信息保存成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    @GetMapping("/queryBuyList")
    public Map<String, Object> queryBuyList(@RequestParam(defaultValue = "1") Integer pageNum,
                                            @RequestParam(defaultValue = "6") Integer pageSize) {
        return buyListService.queryBuyListService(pageNum, pageSize);
    }

    @PostMapping("/updateBuyList")
    public Map<String, Object> updateBuyList(@RequestBody BuyList buyList) {
        Map<String, Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            buyList.setBuyTime(new Date());
            buyListService.updateById(buyList);
            result.put("code",200);
            result.put("msg","采购单信息更新成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理采购单删除的ajax请求*/
    @RequestMapping("/deleteBuyList/{id}")
    public Map<String, Object> deleteBuyList(@PathVariable Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("code",400);
        result.put("msg","操作失败！");
        try{
            buyListService.removeById(id);
            result.put("code",200);
            result.put("msg","采购单信息删除成功！");
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    /*处理数据导出excel请求，下载excel文件*/
    @GetMapping("/exportExcel")
    public ResponseEntity exportExcel() {
        XSSFWorkbook workbook = buyListService.exportExcelService();
        //将workbook，excel文件对象，封装到字节数组
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try{
            workbook.write(baos);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        //获得字节数组中封装的文件
        byte[] bytes = baos.toByteArray();
        //创建HttpHeaders对象封装响应头
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); //响应体的类型
        //设置下载的文件的名字
        String name="采购单列表.xlsx";
        try{
            name= URLEncoder.encode(name,"UTF-8");
        }catch (UnsupportedEncodingException ex){
            ex.printStackTrace();
        }
        headers.add(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+name);
        //创建对象，封装响应体，响应头，状态码
        ResponseEntity result=new ResponseEntity(bytes,headers, HttpStatus.CREATED);
        return result;
    }
}
