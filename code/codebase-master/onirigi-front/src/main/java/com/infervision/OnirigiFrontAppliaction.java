package com.infervision;

import com.infervision.config.RabbitProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午5:32
 * @version:1.0
 **/
@SpringBootApplication
@EnableConfigurationProperties(RabbitProperties.class)
public class OnirigiFrontAppliaction {

    public static void main(String[] args) {

        SpringApplication.run(OnirigiFrontAppliaction.class, args);
    }
}
