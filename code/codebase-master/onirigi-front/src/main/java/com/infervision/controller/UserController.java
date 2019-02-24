package com.infervision.controller;

import com.infervision.constants.ExceptionCode;
import com.infervision.exception.CommonException;
import com.infervision.model.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午7:58
 * @version:1.0 用户Controller
 **/
@RestController
@RequestMapping("users")
@Api(value = "用户controller", tags = {"用户操作接口"})
public class UserController {

    /**
     * 日志组件
     */
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);


    /**
     * @param user 用户
     * @return: com.infervision.model.User
     * @author: fruiqi
     * @date: 19-2-18 下午8:02
     */
    @ApiOperation(value = "获取用户信息", notes = "根据id,姓名")
    @GetMapping()
    public User getUsers(@RequestBody User user) throws CommonException {
        User old = new User();
        old.setAge(10).setId(1).setName("wowow");

        Integer id = user.getId();

        if (id != null) {
            if (id.equals(old.getId())) {
                return old;
            } else {
                throw new CommonException(ExceptionCode.ERROR_COMMON00000);
            }
        } else {
            throw new CommonException(ExceptionCode.ERROR_COMMON00000);
        }

    }


}
