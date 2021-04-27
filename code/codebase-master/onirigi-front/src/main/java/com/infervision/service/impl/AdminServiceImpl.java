package com.infervision.service.impl;

import com.infervision.dao.AdminDtoMapper;
import com.infervision.exception.CommonException;
import com.infervision.model.AdminDto;
import com.infervision.service.AdminService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;

import static com.infervision.constants.ExceptionCode.ERROR_REGISTRYPASSWORD100011;
import static com.infervision.constants.ExceptionCode.ERROR_REGISTRY_ADMIN_NAME100012;
import static com.infervision.util.ConstantUtil.EMPTY;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/3/9 0:44
 * @Version 1.0
 */
@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    AdminDtoMapper adminDtoMapper;

    @Override
    public AdminDto addAdmin(AdminDto dto) throws CommonException {

        String password = StringUtils.trimToEmpty(dto.getAdminPassword());
        if (EMPTY.equals(password)){
            throw new CommonException(ERROR_REGISTRYPASSWORD100011);
        }

        String name = StringUtils.trimToEmpty(dto.getAdminName());
        if (EMPTY.equals(name)){
            throw new CommonException(ERROR_REGISTRY_ADMIN_NAME100012);
        }

        String salt = RandomStringUtils.randomNumeric(6);
        dto.setAdminSalt(salt);
        String md5Password = DigestUtils.md5DigestAsHex((password + salt).getBytes());
        dto.setAdminPassword(md5Password);
        int adminId = adminDtoMapper.insert(dto);
        dto.setAdminId(Long.parseLong(String.valueOf(adminId)));
        dto.setAdminSalt(null);
        return dto;
    }

    /**
     * @Author fruiqi
     * @Description  genuine管理员查询管理员信息
     * @Date 23:18 2019/3/9
     * @Param [adminName]
     * @return com.infervision.model.AdminDto
     **/
    @Override
    public AdminDto selectAdmin(String adminName) {
        AdminDto dto = adminDtoMapper.selectByAdminName(adminName);
        return dto;
    }
}
