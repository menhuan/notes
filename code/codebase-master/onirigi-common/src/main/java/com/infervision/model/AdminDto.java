package com.infervision.model;

import java.util.Date;

public class AdminDto {
    private Long adminId;

    private String adminName;

    /**
     * 管理员级别 0超级管理员 1普通管理员
     */
    private Integer adminGrade;

    private String adminEmail;

    private String adminPhoneNum;

    private String adminIdentityNum;

    private String adminPassword;

    private String adminSalt;

    private Date createDate;

    private Date updateDate;

    private Integer userId;

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName == null ? null : adminName.trim();
    }

    public Integer getAdminGrade() {
        return adminGrade;
    }

    public void setAdminGrade(Integer adminGrade) {
        this.adminGrade = adminGrade;
    }

    public String getAdminEmail() {
        return adminEmail;
    }

    public void setAdminEmail(String adminEmail) {
        this.adminEmail = adminEmail == null ? null : adminEmail.trim();
    }

    public String getAdminPhoneNum() {
        return adminPhoneNum;
    }

    public void setAdminPhoneNum(String adminPhoneNum) {
        this.adminPhoneNum = adminPhoneNum == null ? null : adminPhoneNum.trim();
    }

    public String getAdminIdentityNum() {
        return adminIdentityNum;
    }

    public void setAdminIdentityNum(String adminIdentityNum) {
        this.adminIdentityNum = adminIdentityNum == null ? null : adminIdentityNum.trim();
    }

    public String getAdminPassword() {
        return adminPassword;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword == null ? null : adminPassword.trim();
    }

    public String getAdminSalt() {
        return adminSalt;
    }

    public void setAdminSalt(String adminSalt) {
        this.adminSalt = adminSalt == null ? null : adminSalt.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}