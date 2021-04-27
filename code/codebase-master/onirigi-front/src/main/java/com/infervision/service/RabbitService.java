package com.infervision.service;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午5:45
 * @version:1.0 rabbit 接口
 **/
public interface RabbitService {


    public void sendMessage(String exchange, String routingKey,String message);
}
