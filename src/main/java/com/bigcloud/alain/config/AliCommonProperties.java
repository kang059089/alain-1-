package com.bigcloud.alain.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * 阿里服务通用参数配置
 */
@ConfigurationProperties(prefix = "ali.common")
public class AliCommonProperties {

    private final Logger log = LoggerFactory.getLogger(AliCommonProperties.class);

    /** RAM账号的AccessKey ID */
    private String accessKeyId;
    /** RAM账号Access Key Secret */
    private String accessKeySecret;

    public String getAccessKeyId() {
        return accessKeyId;
    }

    public void setAccessKeyId(String accessKeyId) {
        this.accessKeyId = accessKeyId;
    }

    public String getAccessKeySecret() {
        return accessKeySecret;
    }

    public void setAccessKeySecret(String accessKeySecret) {
        this.accessKeySecret = accessKeySecret;
    }

    /**
     * PostContruct是spring框架的注解，在方法上加该注解会在项目启动的时候执行该方法，也可以理解为在spring容器初始化的时候执行该方法。
     */
    @PostConstruct
    public void init() {
        log.info(description());
    }

    public String description() {
        StringBuilder sb = new StringBuilder("\nConfigs{\n  ");
        sb.append("RAM账号的AccessKey ID: ").append(accessKeyId).append("\n");
        sb.append(", RAM账号Access Key Secret: ").append(accessKeySecret).append("\n");
        sb.append("}");
        return sb.toString();
    }
}
