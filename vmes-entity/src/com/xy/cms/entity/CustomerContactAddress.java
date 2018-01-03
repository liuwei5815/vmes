package com.xy.cms.entity;

public class CustomerContactAddress implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 5170112994557400182L;

    /** id */
    private String id;

    /** 《customer_contact》的主键id */
    private String ccId;

    /** 收货地址的行政区划编码,《sys_region》的区县行政计划编码 */
    private long addressCode;

    /** 比如取的洪山区，要记录湖北省武汉市洪山区 */
    private String addressLongname;

    /** 具体的街道名称、小区、楼栋 */
    private String concreteAddress;

    /** 是否默认为1个地址    0：不是，1：是。注：同1个人只能有1个1 */
    private int isdefault;

    /**
     * 获取id
     * 
     * @return id
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置id
     * 
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取《customer_contact》的主键id
     * 
     * @return 《customer_contact》的主键id
     */
    public String getCcId() {
        return this.ccId;
    }

    /**
     * 设置《customer_contact》的主键id
     * 
     * @param ccId
     *          《customer_contact》的主键id
     */
    public void setCcId(String ccId) {
        this.ccId = ccId;
    }

    /**
     * 获取收货地址的行政区划编码,《sys_region》的区县行政计划编码
     * 
     * @return 收货地址的行政区划编码
     */
    public long getAddressCode() {
        return this.addressCode;
    }

    /**
     * 设置收货地址的行政区划编码,《sys_region》的区县行政计划编码
     * 
     * @param addressCode
     *          收货地址的行政区划编码
     */
    public void setAddressCode(long addressCode) {
        this.addressCode = addressCode;
    }

    /**
     * 获取比如取的洪山区，要记录湖北省武汉市洪山区
     * 
     * @return 比如取的洪山区
     */
    public String getAddressLongname() {
        return this.addressLongname;
    }

    /**
     * 设置比如取的洪山区，要记录湖北省武汉市洪山区
     * 
     * @param addresslongname
     *          比如取的洪山区
     */
    public void setAddressLongname(String addressLongname) {
        this.addressLongname = addressLongname;
    }

    /**
     * 获取具体的街道名称、小区、楼栋
     * 
     * @return 具体的街道名称、小区、楼栋
     */
    public String getConcreteAddress() {
        return this.concreteAddress;
    }

    /**
     * 设置具体的街道名称、小区、楼栋
     * 
     * @param concreteAddress
     *          具体的街道名称、小区、楼栋
     */
    public void setConcreteAddress(String concreteAddress) {
        this.concreteAddress = concreteAddress;
    }

    /**
     * 获取是否默认为1个地址    0：不是，1：是。注：同1个人只能有1个1
     * 
     * @return 是否默认为1个地址    0：不是
     */
    public int getIsdefault() {
        return this.isdefault;
    }

    /**
     * 设置是否默认为1个地址    0：不是，1：是。注：同1个人只能有1个1
     * 
     * @param isdefault
     *          是否默认为1个地址    0：不是
     */
    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }
}