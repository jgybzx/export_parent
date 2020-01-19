package com.jgybzx.domain.export;


import com.jgybzx.domain.BaseEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ExportProduct extends BaseEntity implements Serializable{

	private String id;
	private String productNo;
	private String packingUnit;			//PCS/SETS
	private Integer cnumber;
	private Integer boxNum;
	private Double grossWeight; //毛重
	private Double netWeight; //净重
	private Double sizeLength;
	private Double sizeWidth;
	private Double sizeHeight;
	private Double exPrice;			//sales confirmation 中的价格（手填）
	private Double price;
	private Double tax;			//收购单价=合同单价
	private Integer orderNo;
	private String exportId;			//报运货物和报运的关系，多对一
	private String factoryName;		//厂家名称，冗余字段
	private String factoryId;
	private List<ExtEproduct> extEproducts = new ArrayList<>();		//报运货物和报运附件的关系，一对多

	public ExportProduct() {
	}

	public ExportProduct(String id, String productNo, String packingUnit, Integer cnumber, Integer boxNum, Double grossWeight, Double netWeight, Double sizeLength, Double sizeWidth, Double sizeHeight, Double exPrice, Double price, Double tax, Integer orderNo, String exportId, String factoryName, String factoryId, List<ExtEproduct> extEproducts) {
		this.id = id;
		this.productNo = productNo;
		this.packingUnit = packingUnit;
		this.cnumber = cnumber;
		this.boxNum = boxNum;
		this.grossWeight = grossWeight;
		this.netWeight = netWeight;
		this.sizeLength = sizeLength;
		this.sizeWidth = sizeWidth;
		this.sizeHeight = sizeHeight;
		this.exPrice = exPrice;
		this.price = price;
		this.tax = tax;
		this.orderNo = orderNo;
		this.exportId = exportId;
		this.factoryName = factoryName;
		this.factoryId = factoryId;
		this.extEproducts = extEproducts;
	}

	@Override
	public String toString() {
		return "ExportProduct{" +
				"id='" + id + '\'' +
				", productNo='" + productNo + '\'' +
				", packingUnit='" + packingUnit + '\'' +
				", cnumber=" + cnumber +
				", boxNum=" + boxNum +
				", grossWeight=" + grossWeight +
				", netWeight=" + netWeight +
				", sizeLength=" + sizeLength +
				", sizeWidth=" + sizeWidth +
				", sizeHeight=" + sizeHeight +
				", exPrice=" + exPrice +
				", price=" + price +
				", tax=" + tax +
				", orderNo=" + orderNo +
				", exportId='" + exportId + '\'' +
				", factoryName='" + factoryName + '\'' +
				", factoryId='" + factoryId + '\'' +
				", extEproducts=" + extEproducts +
				", createBy='" + createBy + '\'' +
				", createDept='" + createDept + '\'' +
				", createTime=" + createTime +
				", updateBy='" + updateBy + '\'' +
				", updateTime=" + updateTime +
				", companyId='" + companyId + '\'' +
				", companyName='" + companyName + '\'' +
				'}';
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getProductNo() {
		return this.productNo;
	}
	public void setProductNo(String productNo) {
		this.productNo = productNo;
	}
	public String getPackingUnit() {
		return this.packingUnit;
	}
	public void setPackingUnit(String packingUnit) {
		this.packingUnit = packingUnit;
	}	
	
	public Integer getCnumber() {
		return this.cnumber;
	}
	public void setCnumber(Integer cnumber) {
		this.cnumber = cnumber;
	}	
	
	public Integer getBoxNum() {
		return this.boxNum;
	}
	public void setBoxNum(Integer boxNum) {
		this.boxNum = boxNum;
	}	
	
	public Double getGrossWeight() {
		return this.grossWeight;
	}
	public void setGrossWeight(Double grossWeight) {
		this.grossWeight = grossWeight;
	}	
	
	public Double getNetWeight() {
		return this.netWeight;
	}
	public void setNetWeight(Double netWeight) {
		this.netWeight = netWeight;
	}	
	
	public Double getSizeLength() {
		return this.sizeLength;
	}
	public void setSizeLength(Double sizeLength) {
		this.sizeLength = sizeLength;
	}	
	
	public Double getSizeWidth() {
		return this.sizeWidth;
	}
	public void setSizeWidth(Double sizeWidth) {
		this.sizeWidth = sizeWidth;
	}	
	
	public Double getSizeHeight() {
		return this.sizeHeight;
	}
	public void setSizeHeight(Double sizeHeight) {
		this.sizeHeight = sizeHeight;
	}	
	
	public Double getExPrice() {
		return this.exPrice;
	}
	public void setExPrice(Double exPrice) {
		this.exPrice = exPrice;
	}	
	
	public Double getPrice() {
		return this.price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}	
	
	public Double getTax() {
		return this.tax;
	}
	public void setTax(Double tax) {
		this.tax = tax;
	}	
	
	public Integer getOrderNo() {
		return this.orderNo;
	}
	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public String getExportId() {
		return exportId;
	}

	public void setExportId(String exportId) {
		this.exportId = exportId;
	}

	public String getFactoryId() {
		return factoryId;
	}

	public void setFactoryId(String factoryId) {
		this.factoryId = factoryId;
	}

	public List<ExtEproduct> getExtEproducts() {
		return extEproducts;
	}

	public void setExtEproducts(List<ExtEproduct> extEproducts) {
		this.extEproducts = extEproducts;
	}

	public String getFactoryName() {
		return factoryName;
	}

	public void setFactoryName(String factoryName) {
		this.factoryName = factoryName;
	}
}
