package com.infervision.service;

import com.infervision.model.AdminDto;

/**
 * @ClassName fruiqi
 * @Description 管理员接口
 * @Author frq
 * @Date 2019/3/9 1:41
 * @Version 1.0
 */
public interface AdminShiroService {

    /**
     * @Author fruiqi
     * @Description   根据管理员名字查询管理员信息
     * @Date 1:47 2019/3/9
     * @Param [adminName]
     * @return com.infervision.model.AdminDto
     **/
    public AdminDto selectAdminByAdminName(String adminName);

}
