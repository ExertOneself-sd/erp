package com.erp.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.erp.mapper.*;
import com.erp.pojo.*;
import com.erp.service.BuyListService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
* @author hzr
* @description 针对表【buy_list(采购单)】的数据库操作Service实现
* @createDate 2025-05-29 11:12:43
*/
@Service
public class BuyListServiceImpl extends ServiceImpl<BuyListMapper, BuyList>
    implements BuyListService{

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private StoreMapper storeMapper;
    @Autowired
    private SupplyMapper supplyMapper;
    @Autowired
    private PlaceMapper placeMapper;
    @Autowired
    private BuyListMapper buylistMapper;

    @Override
    public Map<String, Object> queryAutoDataBuyService(Integer id){
        Map<String, Object> result = new HashMap<>();

        //查询商品信息
        Item item=itemMapper.selectById(id);
        result.put("id",item.getId());
        result.put("itemName",item.getItemName());
        //查询仓库信息
        Integer storeId=item.getStoreId();
        Store store=storeMapper.selectById(storeId);
        result.put("storeId",store.getStoreId());
        result.put("storeName",store.getStoreName());
        //查询供应商信息
        Integer supplyId=item.getSupplyId();
        Supply supply=supplyMapper.selectById(supplyId);
        result.put("supplyId",supply.getSupplyId());
        result.put("supplyName",supply.getSupplyName());
        //查询产地信息
        Integer placeId=item.getPlaceId();
        Place place=placeMapper.selectById(placeId);
        result.put("placeId",place.getPlaceId());
        result.put("placeName",place.getPlaceName());
        return result;
    }

    @Autowired
    private BuyListMapper buyListMapper;

    @Override
    public Map<String, Object> queryBuyListService(Integer pageNum, Integer pageSize){
        Page<Object> page= PageHelper.startPage(pageNum,pageSize);
        List<BuyList> buyLists=buyListMapper.queryBuyListMapper();
        Map<String,Object> result=new HashMap<>();
        result.put("total",page.getTotal());
        result.put("buyLists",buyLists);
        return result;
    }


    @Override
    public XSSFWorkbook exportExcelService(){

        XSSFWorkbook xwb=new XSSFWorkbook();
        //在excel文件中添加sheet表
        XSSFSheet sheet=xwb.createSheet("采购单信息");
        //sheet添加行
        XSSFRow row0=sheet.createRow(0);
        //给第一行添加列
        XSSFCell row0cell0=row0.createCell(0);
        row0cell0.setCellValue("采购单列表");
        //设置单元内容居中
        XSSFCellStyle style=xwb.createCellStyle();
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFillForegroundColor(new XSSFColor(new java.awt.Color(255,0,0),null));
        row0cell0.setCellStyle(style);
        //合并第一行，跨列合并列,从第一行合并8列
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,7));

        XSSFRow row1=sheet.createRow(1);

        row1.createCell(0).setCellValue("仓库名称");
        row1.createCell(1).setCellValue("商品名称");
        row1.createCell(2).setCellValue("预计采购数量");
        row1.createCell(3).setCellValue("实际采购数量");
        row1.createCell(4).setCellValue("采购人");
        row1.createCell(5).setCellValue("采购时间");
        row1.createCell(6).setCellValue("采购人电话");
        row1.createCell(7).setCellValue("状态");

        //查询数据库，获得需要填充的数据
        Page<Object> page= PageHelper.startPage(1,0);
        List<BuyList> buyLists=buyListMapper.queryBuyListMapper();
        int index=2;
        for(BuyList buy:buyLists){
            //每循环遍历一次,创建一行
            XSSFRow rown= sheet.createRow(index);
            rown.createCell(0).setCellValue(buy.getStoreName());
            rown.createCell(1).setCellValue(buy.getItemName());
            rown.createCell(2).setCellValue(buy.getBuyNum());
            rown.createCell(3).setCellValue(buy.getFactBuyNum());
            rown.createCell(4).setCellValue(buy.getBuyUser());

            //创建一个日期格式
            XSSFCell cell=rown.createCell(5);
            CellStyle dateStyle=xwb.createCellStyle();
            short dateFormat=xwb.getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss");
            dateStyle.setDataFormat(dateFormat);
            cell.setCellStyle(dateStyle);
            cell.setCellValue(buy.getBuyTime());


            rown.createCell(6).setCellValue(buy.getPhone());
            rown.createCell(7).setCellValue("0".equals(buy.getIsIn())? "未入库" : "已入库");

            index++;
        }

        return xwb;
    }
}




