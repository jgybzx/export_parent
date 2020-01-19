package com.jgybzx.domain.cargo;



import com.jgybzx.domain.BaseEntity;

import java.io.Serializable;
import java.util.Date;

/**
 * 购销合同实体类
 */

public class Contract extends BaseEntity implements Serializable  {
	private String id;
	private String offeror;			//收购方
	private String contractNo;		//合同号，订单号
	private Date signingDate;		//签单日期
	private String inputBy;			//签单人
	private String checkBy;			//审单人
	private String inspector;		//验货员
	private Double totalAmount;		//总金额=货物的总金额+附件的总金额    冗余字段，为了进行分散计算
	private String crequest;		//要求
	private String customName;		//客户名称
	private Date shipTime;			//船期
	private Integer importNum;		//重要程度
	private Date deliveryPeriod;	//交货期限
	private Integer oldState;		//旧的状态，报运
	private Integer outState;		//出货状态，报运
	private String tradeTerms;		//贸易条款
	private String printStyle;		//打印板式，1打印一个货物2打印两个货物
	private String remark;			//备注
	private Integer state;			//状态：0草稿 1已上报待报运	2 已报运
	private Integer proNum; //货物数量
	private Integer extNum; //附件数量

	public Contract(){}

	public Contract(String id){
		this.id = id;
	}

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getOfferor() {
		return offeror;
	}
	public void setOfferor(String offeror) {
		this.offeror = offeror;
	}
	public String getContractNo() {
		return contractNo;
	}
	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}
	public Date getSigningDate() {
		return signingDate;
	}
	public void setSigningDate(Date signingDate) {
		this.signingDate = signingDate;
	}
	public String getInputBy() {
		return inputBy;
	}
	public void setInputBy(String inputBy) {
		this.inputBy = inputBy;
	}
	public String getCheckBy() {
		return checkBy;
	}
	public void setCheckBy(String checkBy) {
		this.checkBy = checkBy;
	}
	public String getInspector() {
		return inspector;
	}
	public void setInspector(String inspector) {
		this.inspector = inspector;
	}
	public Double getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(Double totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getCrequest() {
		return crequest;
	}
	public void setCrequest(String crequest) {
		this.crequest = crequest;
	}
	public String getCustomName() {
		return customName;
	}
	public void setCustomName(String customName) {
		this.customName = customName;
	}
	public Date getShipTime() {
		return shipTime;
	}
	public void setShipTime(Date shipTime) {
		this.shipTime = shipTime;
	}
	public Integer getImportNum() {
		return importNum;
	}
	public void setImportNum(Integer importNum) {
		this.importNum = importNum;
	}
	public Date getDeliveryPeriod() {
		return deliveryPeriod;
	}
	public void setDeliveryPeriod(Date deliveryPeriod) {
		this.deliveryPeriod = deliveryPeriod;
	}
	public Integer getOldState() {
		return oldState;
	}
	public void setOldState(Integer oldState) {
		this.oldState = oldState;
	}
	public Integer getOutState() {
		return outState;
	}
	public void setOutState(Integer outState) {
		this.outState = outState;
	}
	public String getTradeTerms() {
		return tradeTerms;
	}
	public void setTradeTerms(String tradeTerms) {
		this.tradeTerms = tradeTerms;
	}
	public String getPrintStyle() {
		return printStyle;
	}
	public void setPrintStyle(String printStyle) {
		this.printStyle = printStyle;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getCompanyId() {
		return companyId;
	}
	public void setCompanyId(String companyId) {
		this.companyId = companyId;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public Integer getProNum() {
		return proNum;
	}

	public void setProNum(Integer proNum) {
		this.proNum = proNum;
	}

	public Integer getExtNum() {
		return extNum;
	}

	public void setExtNum(Integer extNum) {
		this.extNum = extNum;
	}

	@Override
	public String toString() {
		return "Contract{" +
				"id='" + id + '\'' +
				", offeror='" + offeror + '\'' +
				", contractNo='" + contractNo + '\'' +
				", signingDate=" + signingDate +
				", inputBy='" + inputBy + '\'' +
				", checkBy='" + checkBy + '\'' +
				", inspector='" + inspector + '\'' +
				", totalAmount=" + totalAmount +
				", crequest='" + crequest + '\'' +
				", customName='" + customName + '\'' +
				", shipTime=" + shipTime +
				", importNum=" + importNum +
				", deliveryPeriod=" + deliveryPeriod +
				", oldState=" + oldState +
				", outState=" + outState +
				", tradeTerms='" + tradeTerms + '\'' +
				", printStyle='" + printStyle + '\'' +
				", remark='" + remark + '\'' +
				", state=" + state +
				", proNum=" + proNum +
				", extNum=" + extNum +
				'}';
	}

	public Contract(String id, String offeror, String contractNo, Date signingDate, String inputBy, String checkBy, String inspector, Double totalAmount, String crequest, String customName, Date shipTime, Integer importNum, Date deliveryPeriod, Integer oldState, Integer outState, String tradeTerms, String printStyle, String remark, Integer state, Integer proNum, Integer extNum) {
		this.id = id;
		this.offeror = offeror;
		this.contractNo = contractNo;
		this.signingDate = signingDate;
		this.inputBy = inputBy;
		this.checkBy = checkBy;
		this.inspector = inspector;
		this.totalAmount = totalAmount;
		this.crequest = crequest;
		this.customName = customName;
		this.shipTime = shipTime;
		this.importNum = importNum;
		this.deliveryPeriod = deliveryPeriod;
		this.oldState = oldState;
		this.outState = outState;
		this.tradeTerms = tradeTerms;
		this.printStyle = printStyle;
		this.remark = remark;
		this.state = state;
		this.proNum = proNum;
		this.extNum = extNum;
	}
}
