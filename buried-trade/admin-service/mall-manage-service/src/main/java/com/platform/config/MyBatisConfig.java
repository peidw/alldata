package com.platform.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置类
 * @author AllDataDC
 */
@Configuration
@EnableTransactionManagement
@EnableAutoConfiguration
@MapperScan({"com.platform.mall.mapper", "com.platform.mall.mapper.admin", "com.platform.manage.mapper"})
public class MyBatisConfig {
}

