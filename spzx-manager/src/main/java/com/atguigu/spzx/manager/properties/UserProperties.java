package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @ClassName UserProperties
 * @Description
 * @Author Richard
 * @Date 2023-12-14 22:33
 **/
@Data
@ConfigurationProperties(prefix = "spzx.auth")
public class UserProperties {
    private List<String> noAuthUrls;
}
