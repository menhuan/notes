package com.infervision.constants;

import io.swagger.annotations.ApiModelProperty;
import org.slf4j.LoggerFactory;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: fruiqi
 * @date: 19-2-19 上午10:25
 * @version:1.0
 **/
public enum ExceptionCode {

    ERROR_COMMON000000("000000", "返回的错误码不存在"),

    /**********************登陆注册异常信息*********************/
    ERROR_REGISTRYPASSWORD100011("100011","注册密码为空"),
    ERROR_REGISTRY_ADMIN_NAME100012("100012","注册管理员为空"),
    ERROR_CHECK_NAME_ERROR100013("100013","管理员名字为空"),
    ERROR_CHECK_NAME_ERROR100014("100014","用户名或者密码错误"),
    ERROR_CHECK_NAME_ERROR100015("100015","管理员名字不正确")



    ;

    /**
     * 错误码
     */
    private String code = "";

    /**
     * 异常信息
     */
    private String info = "";


    ExceptionCode(String code, String info) {
        this.code = code;
        this.info = info;
    }

    /**
     * 存储异常类型
     */
    private static Map<String, ExceptionCode> map = new HashMap<String, ExceptionCode>();

    static {
        EnumSet<ExceptionCode> enumSet;
        enumSet = EnumSet.allOf(ExceptionCode.class);
        for (ExceptionCode type : enumSet) {
            map.put(type.getCode(), type);
        }
    }

    /**
     * 通过code获得 对应的返回码类型数据
     */

    public static ExceptionCode getReturnCodeType(String code) {
        if (map.containsKey(code)) {
            return map.get(code);
        } else {
            LoggerFactory.getLogger(ExceptionCode.class).error("返回的错误码不存在：" + code);
        }

        return ERROR_COMMON000000;
    }

    @ApiModelProperty(example = "000000", value = "错误code", required = true)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @ApiModelProperty(example = "请求参数错误", value = "详情内容", required = true)
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
