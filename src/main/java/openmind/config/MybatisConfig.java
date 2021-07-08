package openmind.config;

import java.io.IOException;
import java.util.Objects;
import java.util.Properties;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan(basePackages = { "openmind.service" })
public class MybatisConfig {

    @Autowired
    private Environment env;

    // dataSource
    @Bean
    public DriverManagerDataSource dataSource() throws IOException {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        Properties connectionProperties = new Properties();
        connectionProperties.put("serverTimezone", "UTC");
        dataSource.setConnectionProperties(connectionProperties);
        return dataSource;
    }

    // load MyBatis Configuration
//    @Bean
//    public SqlSessionFactoryBean sqlSessionFactory() throws Exception {
//        Resource resource = new ClassPathResource("SqlMapConfig.xml");
//        SqlSessionFactoryBean sqlSessionFactory = new SqlSessionFactoryBean();
//        sqlSessionFactory.setDataSource(dataSource());
//        sqlSessionFactory.setConfigLocation(resource);
//        return sqlSessionFactory;
//    }

    // scan all mapper annotation in package
//    @Bean
//    public MapperScannerConfigurer mapperScannerConfigurer() {
//        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
//        mapperScannerConfigurer.setBasePackage("vn.viettuts.mapper");
//        mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
//        return mapperScannerConfigurer;
//    }
}
