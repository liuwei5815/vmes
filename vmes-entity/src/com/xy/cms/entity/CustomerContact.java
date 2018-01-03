package com.xy.cms.entity;


public class CustomerContact implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = -6232507414505019338L;

    /** id */
    private String id;

    /** <customer>表的客户id */
    private long customerId;

    /** 联系人名称 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 电话 */
    private String tel;

    /** 是否默认 0:非默认 1：默认 */
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
     * 获取<customer>表的客户id
     * 
     * @return <customer>表的客户id
     */
    public long getCustomerId() {
        return this.customerId;
    }

    /**
     * 设置<customer>表的客户id
     * 
     * @param customerId
     *          <customer>表的客户id
     */
    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    /**
     * 获取联系人名称
     * 
     * @return 联系人名称
     */
    public String getName() {
        return this.name;
    }

    /**
     * 设置联系人名称
     * 
     * @param name
     *          联系人名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取手机号
     * 
     * @return 手机号
     */
    public String getPhone() {
        return this.phone;
    }

    /**
     * 设置手机号
     * 
     * @param phone
     *          手机号
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 获取电话
     * 
     * @return 电话
     */
    public String getTel() {
        return this.tel;
    }

    /**
     * 设置电话
     * 
     * @param tel
     *          电话
     */
    public void setTel(String tel) {
        this.tel = tel;
    }

    /**
     * 获取是否默认 0:非默认 1：默认
     * 
     * @return 是否默认 0:非默认 1
     */
    public int getIsdefault() {
        return this.isdefault;
    }

    /**
     * 设置是否默认 0:非默认 1：默认
     * 
     * @param isdefault
     *          是否默认 0:非默认 1
     */
    public void setIsdefault(int isdefault) {
        this.isdefault = isdefault;
    }
}
