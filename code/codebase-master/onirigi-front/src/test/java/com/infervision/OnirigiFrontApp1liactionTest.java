package com.infervision;


import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.metadata.BaseRowModel;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.infervision.model.CustomerMessage;
import com.infervision.model.OnlineData;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName fruiqi
 * @Description
 * @Author frq
 * @Date 2019/5/13 23:47
 * @Version 1.0
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = OnirigiFrontAppliaction.class)
public class OnirigiFrontApp1liactionTest {


    @Test
    public void test1() throws FileNotFoundException {
        OutputStream out = new FileOutputStream("F:/vscode/78.xlsx");
        try {
            ExcelWriter writer = new ExcelWriter(out, ExcelTypeEnum.XLSX);
            //写第一个sheet, sheet1  数据全是List<String> 无模型映射关系
            Sheet sheet1 = new Sheet(1, 0, CustomerMessage.class);
            writer.write(getData(), sheet1);
            writer.finish();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @return java.util.List<?       extends       com.alibaba.excel.metadata.BaseRowModel>
     * @Author fruiqi
     * @Description 获取数据
     * @Date 23:50 2019/5/13
     **/
    private List<? extends BaseRowModel> getData() {
        CustomerMessage message = new CustomerMessage();
        message.setP1("1");
        message.setP2("2");
        message.setP3("3");
        message.setP4("4");
        message.setP5("5");
        message.setP6("6");
        message.setP7("P7");

        List<OnlineData>  list1 = new ArrayList<>();
        OnlineData onlineData = new OnlineData();
        onlineData.setP8("123");
        onlineData.setP9("345");

        OnlineData onlineData1 = new OnlineData();
        onlineData1.setP8("213");
        onlineData1.setP9("789");

        list1.add(onlineData);
        list1.add(onlineData1);

        List<CustomerMessage> list = new ArrayList<>();
        list.add(message);
        CustomerMessage message1 = new CustomerMessage();
        BeanUtils.copyProperties(message, message1);
        message1.setP6("sdasdasdas");
        list.add(message1);
        list.add(message);
        list.add(message1);
        return list;

    }
}
