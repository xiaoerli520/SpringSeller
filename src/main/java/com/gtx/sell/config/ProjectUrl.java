package com.gtx.sell.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@ConfigurationProperties(prefix = "projectUrl") // 取到config的配置文件属性
@Component
public class ProjectUrl {

    public String wechatMpAuthorize;

    public String wechatOpenAuthorize;

    public String sell;

}
