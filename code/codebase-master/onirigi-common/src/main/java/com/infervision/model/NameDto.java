package com.infervision.model;

import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;


public class NameDto {

    @ApiModelProperty(value = "ID",example = "0")
    private Integer id;

    @ApiModelProperty(value = "用户名字",name = "name",example = "0",required = true)
    private String name;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "用户名字",example = "2019-02-08",dataType = "date")
    private Date createDate;

    @ApiModelProperty(value = "用户id",name = "userId",example = "0",required = true)
    private Integer userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}