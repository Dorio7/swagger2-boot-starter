package com.moming.swagger.autoconfigure;

import com.moming.swagger.properties.Swagger2Properties;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

/**
 * 1. ��ȡ�����ļ�
 * 2. �������ļ����ݸ�ֵ�� swagger �� Docket ����
 * 3. �� Docket ����ע�뵽 bean �� ����Ҫʵ�� BeanFactoryAware ��ȡbeanFatory��
 * �ؼ��Ƕ���Ĳ���
 *
 * @author hjy
 **/
@Configuration
@EnableSwagger2
public class Swagger2AutoConfiguration implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * ��ȡ beanFactroy
     * @param beanFactory beanFactory
     * @throws BeansException BeansException
     */
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Bean
    public Swagger2Properties properties() {
        return new Swagger2Properties();
    }


    @Bean("createRestOpenApi")
    public List<Docket> createRestOpenApi(Swagger2Properties properties) {
        ConfigurableBeanFactory configurableBeanFactory = (ConfigurableBeanFactory) beanFactory;
        List<Docket> docketList = new ArrayList<>();
        // swagger ����
        for (String groupName : properties.getGroups().keySet()) {
            Swagger2Properties.GroupInfo groupInfo = properties.getGroups().get(groupName);
            String basePackage = groupInfo.getBasePackage();
            Docket docket = new Docket(DocumentationType.SWAGGER_2)
                    .groupName(groupName)
                    .apiInfo(openApiInfo(groupInfo))
                    .select()
                    .apis(RequestHandlerSelectors.basePackage(basePackage))
                    .paths(PathSelectors.any())
                    .build();
            docketList.add(docket);
            // docket ���� IOC ����
            configurableBeanFactory.registerSingleton(groupName, docket);
        }
        return docketList;
    }


    private ApiInfo openApiInfo(Swagger2Properties.GroupInfo groupInfo) {
        return new ApiInfoBuilder()
                .title(groupInfo.getTitle())
                .description(groupInfo.getDescription())
                .version("1.0")
                .build();
    }


}
