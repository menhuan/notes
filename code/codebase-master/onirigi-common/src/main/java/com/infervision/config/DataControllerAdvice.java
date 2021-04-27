package com.infervision.config;


import com.infervision.exception.CommonException;
import com.infervision.model.ResponseExceptionBody;
import org.apache.shiro.ShiroException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 异常处理
 * @author: fruiqi
 * @date: 19-2-19 上午11:30
 * @version:1.0
 **/
@RestControllerAdvice
public class DataControllerAdvice {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(DataControllerAdvice.class);


    /**
     * 处理自定义返回数据
     *
     * @param e 自定义异常返回数据
     * @return: com.infervision.model.ResponseExceptionBody
     * @author: fruiqi
     * @date: 19-2-19 下午12:00
     */
    @ExceptionHandler(CommonException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseExceptionBody handleException(CommonException e, HttpServletRequest request) {
        logger.error("[ERROR] error message {} ", e.getCode().getInfo());
        ResponseExceptionBody body = new ResponseExceptionBody(request, e.getCode() );
        return body;
    }   
    
    /**
     * @Author fruiqi
     * @Description  处理 shiro 异常信息
     * @Date 1:20 2019/3/9
     * @Param [e, request]
     * @return com.infervision.model.ResponseExceptionBody
     **/
    @ExceptionHandler(ShiroException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseExceptionBody handleShiroException(ShiroException e,HttpServletRequest request){
        return new ResponseExceptionBody(e.getMessage(),request);
    }

    /**
     * @Author fruiqi
     * @Description  处理没有权限异常
     * @Date 1:20 2019/3/9
     * @Param [e, request]
     * @return com.infervision.model.ResponseExceptionBody
     **/
    @ExceptionHandler(UnauthorizedException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseExceptionBody handleUnauthException(UnauthorizedException e,HttpServletRequest request){
        return new ResponseExceptionBody(e.getMessage(),request);
    }
    
    /**
     * @Author fruiqi
     * @Description  捕捉全局异常信息
     * @Date 1:22 2019/3/9
     * @Param [e, request]
     * @return com.infervision.model.ResponseExceptionBody
     **/
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseExceptionBody handleException(Exception e, HttpServletRequest request) {
        logger.error("[ERROR] error message {} ", e.getMessage());
        ResponseExceptionBody body = new ResponseExceptionBody(e.getMessage() ,request );
        return body;
    }


}
