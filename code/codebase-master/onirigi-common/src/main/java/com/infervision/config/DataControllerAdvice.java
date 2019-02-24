package com.infervision.config;


import com.infervision.exception.CommonException;
import com.infervision.model.ResponseExceptionBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
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
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseExceptionBody handleException(CommonException e, HttpServletRequest request) {
        logger.error("[ERROR] error message {} ", e.getMessage());
        ResponseExceptionBody body = new ResponseExceptionBody(request, e.getCode() );
        return body;
    }
}
