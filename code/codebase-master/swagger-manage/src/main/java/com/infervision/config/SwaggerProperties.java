package com.infervision.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author: fruiqi
 * @date: 19-2-18 下午6:23
 * @version:1.0
 **/
@Configuration
public class SwaggerProperties {

    /**
     * 标题 默认
     */
    @Value("${swagger.title}")
    private String title = "标题";

    /**
     * 描述
     */
    @Value("${swagger.description}")
    private String description = "描述";

    /**
     * 版本号
     */
    @Value("${swagger.version}")
    private String version = "1.0.0";

    /**
     * 包路径信息
     */
    @Value("${swagger.basepackage}")
    private String basePacakge = "com.infervision";

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getBasePacakge() {
        return basePacakge;
    }

    public void setBasePacakge(String basePacakge) {
        this.basePacakge = basePacakge;
    }
}
