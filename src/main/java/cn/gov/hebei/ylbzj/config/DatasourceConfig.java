package cn.gov.hebei.ylbzj.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = {"cn.gov.hebei.ylbzj.dao"}, sqlSessionTemplateRef = "selfSqlSessionTemplate")
public class DatasourceConfig {

    /**
     * 数据源配置对象
     * Primary 表示默认的对象，Autowire可注入，不是默认的得明确名称注入
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties selfDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 数据源对象
     *
     * @return
     */
    @Bean
    public DataSource selfDataSource() {
        DataSourceProperties dataSourceProperties = selfDataSourceProperties();
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 配置事物
     *
     * @param selfDataSource
     * @return
     */
    @Bean
    @Resource
    public PlatformTransactionManager selfManager(DataSource selfDataSource) {
        return new DataSourceTransactionManager(selfDataSource);
    }

    /**
     * 配置Mapper路径
     *
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean
    public SqlSessionFactory selfSqlSessionFactory(@Qualifier("selfDataSource") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        //添加XML目录
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            bean.setMapperLocations(resolver.getResources("classpath*:mapper/**Mapper.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Bean
    public SqlSessionTemplate selfSqlSessionTemplate(@Qualifier("selfSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        SqlSessionTemplate template = new SqlSessionTemplate(sqlSessionFactory); // 使用上面配置的Factory
        return template;
    }
}