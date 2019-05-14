package com.infervision.model;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.metadata.BaseRowModel;

import java.util.List;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/5/13 23:38
 * @Version 1.0
 */
public class CustomerMessage extends BaseRowModel {

    @ExcelProperty(value = {"客户信息","医院名称"},index = 0)
    private String p1;

    @ExcelProperty(value = {"客户信息","区域划分"},index = 1)
    private String p2;

    @ExcelProperty(value = {"客户信息","医院编号"},index = 2)
    private String p3;

    @ExcelProperty(value = {"客户信息","城市"},index = 3)
    private String p4;

    @ExcelProperty(value = {"客户信息","医院等级"},index = 4)
    private String p5;

    @ExcelProperty(value = {"客户信息","国家级主委"},index = 5)
    private String p6;

    @ExcelProperty(value = {"客户信息","省级主委"},index = 6)
    private String p7;

    private List<OnlineData> p8;

    public String getP1() {
        return p1;
    }

    public void setP1(String p1) {
        this.p1 = p1;
    }

    public String getP2() {
        return p2;
    }

    public void setP2(String p2) {
        this.p2 = p2;
    }

    public String getP3() {
        return p3;
    }

    public void setP3(String p3) {
        this.p3 = p3;
    }

    public String getP4() {
        return p4;
    }

    public void setP4(String p4) {
        this.p4 = p4;
    }

    public String getP5() {
        return p5;
    }

    public void setP5(String p5) {
        this.p5 = p5;
    }

    public String getP6() {
        return p6;
    }

    public void setP6(String p6) {
        this.p6 = p6;
    }

    public String getP7() {
        return p7;
    }

    public void setP7(String p7) {
        this.p7 = p7;
    }

    public List<OnlineData> getP8() {
        return p8;
    }

    public void setP8(List<OnlineData> p8) {
        this.p8 = p8;
    }
}
