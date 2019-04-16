package com.moming.swagger.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Import;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ��ȡ application �����ļ�
 *
 * @author hjy
 **/
@Data
@ConfigurationProperties("swagger2")
public class Swagger2Properties {

    /**
     * ����
     */
    private Map<String, GroupInfo> groups = new LinkedHashMap<>();


    @Data
    public static class GroupInfo {
        /**
         * swagger ������İ�·��
         */
        private String basePackage;

        /**
         * title
         */
        private String title;

        /**
         * swagger �����˵��
         */
        private String description;
    }
}
