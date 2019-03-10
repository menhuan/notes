package com.infervision.service.impl;

import com.infervision.dao.AdminDtoMapper;
import com.infervision.model.AdminDto;
import com.infervision.service.AdminShiroService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/9 1:47
 * @Version 1.0
 */
@Service
public class AdminServceShiroImpl implements AdminShiroService {

    @Resource
    AdminDtoMapper adminDtoMapper;

    @Override
    public AdminDto selectAdminByAdminName(String adminName) {
        AdminDto adminDto = adminDtoMapper.selectByAdminName(adminName);

        return adminDto;
    }
}
