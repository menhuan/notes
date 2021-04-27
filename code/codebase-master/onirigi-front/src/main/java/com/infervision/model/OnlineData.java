package com.infervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/5/14 0:08
 * @Version 1.0
 */
public class OnlineData extends BaseRowModel {


    @ExcelProperty(value = {"上线情况","上线时间"},index = 7)
    private String p8;

    @ExcelProperty(value = {"上线情况","上线产品"},index = 8)
    private String p9;


    public String getP8() {
        return p8;
    }

    public void setP8(String p8) {
        this.p8 = p8;
    }

    public String getP9() {
        return p9;
    }

    public void setP9(String p9) {
        this.p9 = p9;
    }
}
