package com.infervision.dao;

import com.infervision.model.AdminDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AdminDtoMapper {
    int deleteByPrimaryKey(Long adminId);

    int insert(AdminDto record);

    int insertSelective(AdminDto record);

    AdminDto selectByPrimaryKey(Long adminId);
    
    /**
     * @Author fruiqi
     * @Description  根据管理员名字查询管理员信息
     * @Date 1:49 2019/3/9
     * @Param [adminName]
     * @return com.infervision.model.AdminDto
     **/
    AdminDto selectByAdminName(String adminName);

    int updateByPrimaryKeySelective(AdminDto record);

    int updateByPrimaryKey(AdminDto record);
}