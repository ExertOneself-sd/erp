package com.erp.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 
 * @TableName t_customer
 */
@TableName(value ="t_customer")
@Data
public class Customer {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private String custName;

    /**
     * 
     */
    private String address;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String cutType;

    /**
     * 
     */
    private Integer graed;

    /**
     * 
     */
    private Double hitTotal;
}