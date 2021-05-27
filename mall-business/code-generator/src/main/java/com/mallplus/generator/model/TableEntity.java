package com.mallplus.generator.model;

import java.util.Date;
import java.util.List;

/**
 * @Author: mall
 */
public class TableEntity {
    /**
     * 表的名称
     */
    private String tableName;
    private Date createTime;

    /**
     * 表的备注
     */
    private String comments;
    private String tableComment;
    private String engine;
    /**
     * 表的主键
     */
    private ColumnEntity pk;
    /**
     * 表的列名(不包含主键)
     */
    private List<ColumnEntity> columns;
    /**
     * 类名(第一个字母大写)，如：sys_user => SysUser
     */
    private String className;
    /**
     * 类名(第一个字母小写)，如：sys_user => sysUser
     */
    private String classname;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getTableComment() {
        return tableComment;
    }

    public void setTableComment(String tableComment) {
        this.tableComment = tableComment;
    }

    public String getEngine() {
        return engine;
    }

    public void setEngine(String engine) {
        this.engine = engine;
    }

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public ColumnEntity getPk() {
        return pk;
    }

    public void setPk(ColumnEntity pk) {
        this.pk = pk;
    }

    public List<ColumnEntity> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnEntity> columns) {
        this.columns = columns;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    @Override
    public String toString() {
        return "TableEntity{" +
                "tableName='" + tableName + '\'' +
                ", createTime=" + createTime +
                ", comments='" + comments + '\'' +
                ", tableComment='" + tableComment + '\'' +
                ", engine='" + engine + '\'' +
                ", pk=" + pk +
                ", columns=" + columns +
                ", className='" + className + '\'' +
                ", classname='" + classname + '\'' +
                '}';
    }
}
