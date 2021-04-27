package com.infervision.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.infervision.constants.ExceptionCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

/**
 * @author: fruiqi
 * @date: 19-2-19 上午11:37
 * @version:1.0
 **/
@ApiModel(value = "body",description = "异常实体类")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseExceptionBody {


    /**
     * 出错时间
     */
    protected Calendar timestamp;

    /**
     * 请求URL
     */
    protected String path;

    /**
     * 错误编码
     */
    protected String code;

    /**
     * 错误信息
     */
    protected String message;

    /**
     * 错误信息
     */
    protected ExceptionCode errors;


    public ResponseExceptionBody(HttpServletRequest request, String code, String message) {
        this.timestamp = Calendar.getInstance();
        if (request != null){
            this.path = request.getRequestURI();
        }

        this.code = code;
        this.message = message;

    }

    public ResponseExceptionBody(HttpServletRequest request, String code) {
       this(request,code,null);
    }

    public ResponseExceptionBody(String message,HttpServletRequest request) {
        this(request,null,message);
    }
    public ResponseExceptionBody(HttpServletRequest request,  ExceptionCode errors) {
        this(request,errors.getCode(),errors.getInfo());
    }


    @ApiModelProperty(value = "出错时间戳", required = true)
    public Calendar getTimestamp() {
        return timestamp;
    }

    public ResponseExceptionBody setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
        return this;
    }

    @ApiModelProperty(value = "请求url", required = true)
    public String getPath() {
        return path;
    }

    public ResponseExceptionBody setPath(String path) {
        this.path = path;
        return this;
    }

    @ApiModelProperty(value = "错误code", required = true)
    public String getCode() {
        return code;
    }

    public ResponseExceptionBody setCode(String code) {
        this.code = code;
        return this;
    }

    @ApiModelProperty(value = "错误消息", required = true)
    public String getMessage() {
        return message;
    }

    public ResponseExceptionBody setMessage(String message) {
        this.message = message;
        return this;
    }

    public ExceptionCode getErrors() {
        return errors;
    }

    @ApiModelProperty(value = "设置error信息", required = true)
    public ResponseExceptionBody setErrors(ExceptionCode errors) {
        this.errors = errors;
        return this;
    }
}
