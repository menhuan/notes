package com.infervision.controller;

import com.alibaba.fastjson.JSON;
import com.infervision.model.AdminDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName LoginController
 * @Description TODO
 * @Author frq
 * @Date 2019/3/7 22:52
 * @Version 1.0
 */
@RestController
public class LoginController {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * @Author ASUS
     * @Description  登录接口尝试
     * @Date 22:55 2019/3/7
     * @Param [adminDto]
     * @return com.infervision.model.AdminDto
     **/
    @PostMapping("login")
    public AdminDto login(@RequestBody  AdminDto adminDto){
        logger.info("------用户获取登录token----- {} ", JSON.toJSONString(adminDto));
        String userName = adminDto.getAdminName();
        String password = adminDto.getAdminPassword();

        String digest = DigestUtils.md5DigestAsHex((password+adminDto.getAdminSalt()).getBytes());


    }


}
