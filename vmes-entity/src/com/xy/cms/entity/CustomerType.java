package com.xy.cms.entity;

public class CustomerType implements java.io.Serializable {
    /** 版本号 */
    private static final long serialVersionUID = 152131779459100893L;

    /** id */
    private String id;

    /** 部门id用于数据权限隔离 */
    private long departmentId;

    /** 客户类型 */
    private String typeName;

    /** 说明 */
    private String describe;

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
     * 获取部门id用于数据权限隔离
     * 
     * @return 部门id用于数据权限隔离
     */
    public long getDepartmentId() {
        return this.departmentId;
    }

    /**
     * 设置部门id用于数据权限隔离
     * 
     * @param departmentId
     *          部门id用于数据权限隔离
     */
    public void setDepartmentId(long departmentId) {
        this.departmentId = departmentId;
    }

    /**
     * 获取客户类型
     * 
     * @return 客户类型
     */
    public String getTypeName() {
        return this.typeName;
    }

    /**
     * 设置客户类型
     * 
     * @param typeName
     *          客户类型
     */
    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    /**
     * 获取说明
     * 
     * @return 说明
     */
    public String getDescribe() {
        return this.describe;
    }

    /**
     * 设置说明
     * 
     * @param describe
     *          说明
     */
    public void setDescribe(String describe) {
        this.describe = describe;
    }
}