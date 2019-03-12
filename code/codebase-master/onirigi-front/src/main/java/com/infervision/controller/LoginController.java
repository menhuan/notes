package com.infervision.controller;

import com.alibaba.fastjson.JSON;
import com.infervision.constants.ExceptionCode;
import com.infervision.exception.CommonException;
import com.infervision.model.AdminDto;
import com.infervision.model.Token;
import com.infervision.service.AdminService;
import com.infervision.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

import static com.infervision.util.ConstantUtil.JWT_SECRET;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author frq
 * @Date 2019/3/7 22:52
 * @Version 1.0
 */
@RestController
@Api(value = "loginAndregister", description = "登录与注册")
public class LoginController {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private AdminService adminService;

    /**
     * @return com.infervision.model.AdminDto
     * @Author ASUS
     * @Description 登录接口尝试
     * @Date 22:55 2019/3/7
     * @Param [adminDto]
     **/
    @PostMapping("login/{adminName}/{adminPassword}")
    @ApiResponse(code = 100000, message = "token")
    public Token login(@PathVariable String adminName, @PathVariable String adminPassword) throws CommonException, Exception {
        logger.info("------用户获取登录token----- {} ", StringUtils.join(adminName, " ", adminPassword));
        String userName = adminName;
        String password = adminPassword;
        AdminDto dto = adminService.selectAdmin(userName);
        if (null == dto) {
            throw new CommonException(ExceptionCode.ERROR_CHECK_NAME_ERROR100015);
        }
        String digest = DigestUtils.md5DigestAsHex((password + dto.getAdminSalt()).getBytes());

        if (!digest.equals(dto.getAdminPassword())) {
            throw new CommonException(ExceptionCode.ERROR_CHECK_NAME_ERROR100014);
        }

        String token = null;
        String sign = JwtUtil.sign(dto.getAdminId().toString(),
                dto.getAdminName(), Instant.now().toEpochMilli(), JWT_SECRET);
        Token result = new Token();
        result.setToken(sign);
        return result;
    }

    /**
     * @return com.infervision.model.AdminDto
     * @Author ASUS
     * @Description //TODO 注册 管理信息
     * @Date 0:25 2019/3/9
     * @Param [dto]
     **/
    @PostMapping("register")
    @ApiOperation(value = "注册管理员")
    public AdminDto register(@RequestBody AdminDto dto) throws CommonException {
        logger.info("------------注册管理信息:{}--------", JSON.toJSONString(dto));
        AdminDto adminDto = adminService.addAdmin(dto);
        return adminDto;
    }


    @GetMapping("admins")
    @ApiOperation(value = "获取管理员列表")
    public Set<AdminDto> getAdmins() {
        return new HashSet<>();
    }


}
