package com.infervision.dao;

import com.infervision.model.AdminDto;

public interface AdminDtoMapper {
    int deleteByPrimaryKey(Long adminId);

    int insert(AdminDto record);

    int insertSelective(AdminDto record);

    AdminDto selectByPrimaryKey(Long adminId);

    int updateByPrimaryKeySelective(AdminDto record);

    int updateByPrimaryKey(AdminDto record);
}