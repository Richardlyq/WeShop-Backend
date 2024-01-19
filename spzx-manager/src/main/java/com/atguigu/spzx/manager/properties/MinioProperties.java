package com.atguigu.spzx.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @ClassName MinioProperties
 * @Description
 * @Author Richard
 * @Date 2023-12-17 11:19
 **/

@Data
@ConfigurationProperties(prefix = "spzx.minio") //加入前缀
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
