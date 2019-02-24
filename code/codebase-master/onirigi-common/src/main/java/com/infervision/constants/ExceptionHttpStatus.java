package com.infervision.constants;

import org.springframework.http.HttpStatus;

/**
 * @author: fruiqi
 * @date: 19-2-19 上午10:42
 * @version:1.0 异常状态 常量类
 **/
public class ExceptionHttpStatus {

    /**
     * 默认访问出错都返回该字段错误内容
     */
    public static final HttpStatus ERROR_STATUS = HttpStatus.BAD_REQUEST ;

    /**
     * 默认访问成功之后，都返回 200的状态
     */
    public static final HttpStatus SUCCESS_SATUS = HttpStatus.OK;

}
