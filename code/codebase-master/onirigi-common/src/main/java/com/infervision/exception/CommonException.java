package com.infervision.exception;


import com.infervision.constants.ExceptionCode;
import org.springframework.http.HttpStatus;

/**
 * @author: fruiqi
 * @date: 19-2-19 上午10:39
 * @version:1.0 自定义异常类
 **/
public class CommonException extends Exception {

    /**
     * 默认 是500 异常
     */
    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
    /**
     * 异常 code
     */
    private ExceptionCode code;

    /**
     * 异常消息
     */
    private String message;


    /**
     * 构造方法
     *
     * @param status  状态码
     * @param code    异常code
     * @param message 异常信息
     * @return: CommonException
     * @author: fruiqi
     * @date: 19-2-19 上午10:47
     */
    public CommonException(HttpStatus status, ExceptionCode code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    /**
     * 异常码 固定的构造方法
     *
     * @param code    异常code
     * @param message 异常消息
     * @return:
     * @author: fruiqi
     * @date: 19-2-19 上午10:52
     */
    public CommonException(ExceptionCode code, String message) {
        this(HttpStatus.BAD_REQUEST, code, message);
    }

    public CommonException(ExceptionCode code) {
        this(HttpStatus.BAD_REQUEST, code, null);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public CommonException setStatus(HttpStatus status) {
        this.status = status;
        return this;
    }

    public ExceptionCode getCode() {
        return code;
    }

    public CommonException setCode(ExceptionCode code) {
        this.code = code;
        return this;
    }

    @Override
    public String getMessage() {
        if (this.message != null) {
            return this.message;
        } else {
            return this.code.getInfo();
        }

    }

}
