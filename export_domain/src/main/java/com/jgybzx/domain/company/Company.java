package com.jgybzx.domain.company;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author: guojy
 * @date: 2020/1/2 17:42
 * @Description: ${TODO}
 * @version:
 */
@Data
public class Company implements Serializable {
    private String id;//  ID
    private String name;//  公司名称
    private Date expirationDate;//  到期时间
    private String address;//  公司地址
    private String licenseId;//  营业执照-图片
    private String representative;//  法人代表
    private String phone;//  公司电话
    private String companySize;//  公司规模
    private String industry;//  所属行业
    private String remarks;//  备注
    private Integer state;//  状态
    private Double balance;//  当前余额
    private String city;//  城市
}