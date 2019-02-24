package com.infervision.controller;

import com.alibaba.fastjson.JSON;
import com.infervision.model.NameVo;
import com.infervision.service.RabbitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.infervision.constants.Constants.DEFAULT_TOPIC_TEST_EXCHANGE;
import static com.infervision.constants.Constants.LABEL_FIEL_XML_QUEUE_ROUTING_KEY;

/**
 * @author: fruiqi
 * @date: 19-2-19 下午5:32
 * @version:1.0
 **/

@RequestMapping("name")
@RestController
@Api(value = "名字", tags = {"对名字进行修改"})
public class NameController {

    /**
     * 日志
     */
    private static final Logger logger = LoggerFactory.getLogger(NameController.class);

    @Autowired
    RabbitService rabbitService;

    /**
     * 练习 发送数据到 mq中
     * 1. 发送的数据会到 mq中
     * 2. 我们配置的 listener 是用来消费消息的
     * 3. 客户端配置 可以参考 RabbitClientConfig
     * @param name 名字编号
     * @param vo   实体内容
     * @return: com.infervision.model.NameVo
     * @author: fruiqi
     * @date: 19-2-20 上午10:30
     */
    @ApiOperation(value = "增加name信息", notes = "实体信息")
    @PostMapping(value = "/{name}")
    @ApiImplicitParam(paramType = "query", name = "name", value = "用户名字", required = true, dataType = "string")
    public NameVo addNameVo(@RequestParam String name, @RequestBody NameVo vo) {
        rabbitService.sendMessage(DEFAULT_TOPIC_TEST_EXCHANGE, LABEL_FIEL_XML_QUEUE_ROUTING_KEY, JSON.toJSONString(vo));
        return vo;
    }






}
