package com.infervision.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午6:40
 * @version:1.0 用户
 **/
@ApiModel(value = "User", description = "用户对象")
public class User {



    /**
     * 主键id
     */
    @ApiModelProperty(value = "ID",example = "0")
    private Integer id;

    /**
     * 名字
     */
    @ApiModelProperty(value = "姓名",required = true,example = "fruiqi")
    private String name;


    /**
     * 年龄
     */
    @ApiModelProperty(value = "年龄",example = "0")
    private Integer age ;

    public Integer getId() {
        return id;
    }

    public User setId(Integer id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public User setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getAge() {
        return age;
    }

    public User setAge(Integer age) {
        this.age = age;
        return this;
    }
}
