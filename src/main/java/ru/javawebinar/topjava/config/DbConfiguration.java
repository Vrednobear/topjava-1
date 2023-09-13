package ru.javawebinar.topjava.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.core.io.Resource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@ComponentScan(basePackages={"ru.javawebinar.topjava.service",
        "ru.javawebinar.topjava.repository", "ru.javawebinar.topjava.web"})
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.javawebinar.topjava.repository")
@PropertySource({"classpath:/db/postgres.properties", "classpath:/db/hsqlDB.properties"})
public class DbConfiguration {

    @Value("${database.initScript}")
    Resource initScript;

    @Value("${database.populateScript}")
    Resource populateScript;

    @Value("classpath:db/initHsqlDB.sql")
    Resource hqslInitScript;

    @Bean
    @Profile("Prod")
    public DataSource dataSource(@Value("${database.url}") String url,
                                 @Value("${database.username}") String userName,
                                 @Value("${database.password}") String password)
    {
        org.apache.tomcat.jdbc.pool.DataSource dataSource = new  org.apache.tomcat.jdbc.pool.DataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    @Profile("Prod")
    public DatabasePopulator databasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(initScript, populateScript);
        return populator;
    }

    @Bean
    @Profile("Test")
    public DataSource testDataSource(@Value("${hsql.url}") String url,
                                     @Value("${hsql.username}") String userName,
                                     @Value("${hsql.password}") String password)
    {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.hsqldb.jdbcDriver");
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    @Profile("Test")
    public DatabasePopulator testDatabasePopulator() {
        ResourceDatabasePopulator populator = new ResourceDatabasePopulator();
        populator.addScripts(hqslInitScript, populateScript);
        return populator;
    }

    @Bean
    public DataSourceInitializer dataSourceInitializer(
            @Qualifier("dataSource") DataSource dataSource, @Qualifier("databasePopulator") DatabasePopulator databasePopulator)
    {
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource(dataSource);
        initializer.setEnabled(true);
        initializer.setDatabasePopulator(databasePopulator);
        return initializer;
    }

    @Bean
    @Profile({"jpa", "data"})
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(@Qualifier("dataSource") DataSource dataSource)
    {
        LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
        bean.setDataSource(dataSource);
        bean.setPackagesToScan("ru.javawebinar.**.model");
        bean.setJpaProperties(hibernateProperties());
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(true);
        bean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        return bean;
    }

    @Profile({"jpa", "data"})
    Properties hibernateProperties()
    {
        Properties hibernateProps = new Properties();
        hibernateProps.setProperty("hibernate.format_sql", "true");
        hibernateProps.setProperty("hibernate.use_sql_comments", "true");
//        hibernateProps.setProperty("hibernate.hbm2ddl.auto", "true");
        hibernateProps.setProperty("hibernate.cache.provider_configuration_file_resource_path", "cache/ehcache.xml");
        hibernateProps.setProperty("hibernate.cache.use_second_level_cache", "true");
        hibernateProps.setProperty("hibernate.cache.region.factory_class", "org.hibernate.cache.jcache.internal.JCacheRegionFactory");

        return hibernateProps;
    }

    @Bean
    @Profile({"jpa", "data"})
    public JpaTransactionManager transactionManager(EntityManagerFactory entityManagerFactory)
    {
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(entityManagerFactory);
        return jpaTransactionManager;
    }

    @Bean
    @Profile("jdbc")
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate(@Qualifier("dataSource") DataSource dataSource)
    {
        return new NamedParameterJdbcTemplate(dataSource);
    }

    @Bean
    @Profile("jdbc")
    public JdbcTemplate jdbcTemplate(@Qualifier("dataSource")DataSource dataSource)
    {
        return new JdbcTemplate(dataSource);
    }

}
