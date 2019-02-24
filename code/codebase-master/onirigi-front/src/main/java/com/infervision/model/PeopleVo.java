package com.infervision.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;

import java.util.Date;

/**
 * @author: fruiqi
 * @date: 19-2-20 上午10:38
 * @version:1.0
 **/
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PeopleVo" ,description=" 名字视图展示")
public class PeopleVo  {

    private String name;

    private Date createDate;

    private Integer userId;

    private Integer age;

    private String company;


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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

}
