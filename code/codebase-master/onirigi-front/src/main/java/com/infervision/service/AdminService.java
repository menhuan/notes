package com.infervision.service;

import com.infervision.exception.CommonException;
import com.infervision.model.AdminDto;

/**
 * @Author ASUS
 * @Description //TODO 管理员 service
 * @Date 0:42 2019/3/9
 * @Param
 * @return
 **/
public interface AdminService {
    
    
    /**
     * @Author  增加管理员
     * @Description //TODO 
     * @Date 0:44 2019/3/9
     * @Param [dto]
     * @return com.infervision.model.AdminDto
     **/
    public AdminDto addAdmin(AdminDto dto) throws CommonException;

    /**
     * @Author fruiqi
     * @Description  根据名字查询管理员信息
     * @Date 23:18 2019/3/9
     * @Param [adminName]
     * @return com.infervision.model.AdminDto
     **/
    public AdminDto selectAdmin(String adminName);
}
