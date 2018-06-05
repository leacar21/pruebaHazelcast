package com.company.example.hazelcast.repository.context.config;

import java.beans.PropertyVetoException;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.PooledDataSource;

@Configuration
public class DatabaseConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfig.class);
    private static final String MODEL_PACKAGE = "com.company.example.hazelcast.repository.model";
    private static final String PREFERRED_TEST_QUERY = "/* ping */ SELECT 1";

    @Value("${repository.hibernate.connection.driver_class}")
    private String driverClass;

    @Value("${repository.hibernate.connection.url}")
    private String jdbcUrl;

    @Value("${repository.hibernate.connection.username}")
    private String username;

    @Value("${repository.hibernate.connection.password}")
    private String password;

    @Value("${repository.hibernate.dialect}")
    private String dialect;

    @Value("${repository.hibernate.query.factory_class}")
    private String queryFactoryClass;

    @Value("${repository.hibernate.bytecode.use_reflection_optimizer}")
    private String bytecodeUseReflectionOptimizer;

    @Value("${repository.hibernate.show_sql}")
    private String showSql;

    @Value("${repository.hibernate.format_sql}")
    private String formatSql;

    @Value("${repository.hibernate.generate_statistics}")
    private String generateStatistics;

//    @Value("${repository.hibernate.current_session_context_class}")
//    private String hibernateCurrentSessionContextClass;

    @Value("${repository.hibernate.connection.autocommit}")
    private String hibernateConnectionAutocommit;

    @Value("${repository.hibernate.connection.properties}")
    private String connectionProperties;

    @Value("${repository.hibernate.c3p0.min_size}")
    private int c3p0MinSize;

    @Value("${repository.hibernate.c3p0.max_size}")
    private int c3p0MaxSize;

    @Value("${repository.hibernate.c3p0.idle_test_period}")
    private int c3p0IdleTestPeriod;

    @Value("${repository.hibernate.c3p0.max_connection_age}")
    private int maxConnectionAge;

    @Value("${repository.hibernate.c3p0.max_statements}")
    private int maxStatements;

    @Value("${repository.hibernate.c3p0.max_statements_per_connection}")
    private int maxStatementsPerConnection;

    @Value("${repository.hibernate.c3p0.num_helper_threads}")
    private int numHelperThreads;

    @Value("${repository.hibernate.c3p0.maxIdleTime}")
    private int maxIdleTime;

    @Value("${repository.hibernate.hbm2ddl.auto}")
    private String ddlAuto;

    @Bean(name = "dataSource")
    public LazyConnectionDataSourceProxy getDataSource() throws PropertyVetoException {
        PooledDataSource targetDataSource = this.getMainDataSource();
        LazyConnectionDataSourceProxy dataSource = new LazyConnectionDataSourceProxy(targetDataSource);
        return dataSource;
    }

    @Bean(name = "mainDataSource", destroyMethod = "close")
    public PooledDataSource getMainDataSource() throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        Properties properties = this.parseConnectionProperties();
        if ((properties != null) && !properties.isEmpty()) {
            dataSource.setProperties(properties);
            LOGGER.debug("Using connection properties: {}", dataSource.getProperties());
        }
        dataSource.setDriverClass(this.driverClass);
        dataSource.setJdbcUrl(this.jdbcUrl);
        dataSource.setUser(this.username);
        dataSource.setPassword(this.password);
        dataSource.setMinPoolSize(this.c3p0MinSize);
        dataSource.setMaxPoolSize(this.c3p0MaxSize);
        dataSource.setTestConnectionOnCheckin(false);
        dataSource.setIdleConnectionTestPeriod(this.c3p0IdleTestPeriod);
        dataSource.setPreferredTestQuery(PREFERRED_TEST_QUERY);
        dataSource.setMaxConnectionAge(this.maxConnectionAge);
        dataSource.setMaxStatements(this.maxStatements);
        dataSource.setMaxStatementsPerConnection(this.maxStatementsPerConnection);
        dataSource.setNumHelperThreads(this.numHelperThreads);
        dataSource.setMaxIdleTime(this.maxIdleTime);
        return dataSource;
    }

    @Bean(name = "sessionFactory")
    public LocalSessionFactoryBean getLocalSessionFactoryBean() throws PropertyVetoException {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        String[] packagesToScan = new String[] {MODEL_PACKAGE};
        sessionFactory.setPackagesToScan(packagesToScan);
        DataSource dataSource = this.getDataSource();
        sessionFactory.setDataSource(dataSource);
        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", this.dialect);
        hibernateProperties.setProperty("hibernate.query.factory_class", this.queryFactoryClass);
        hibernateProperties.setProperty("hibernate.bytecode.use_reflection_optimizer", this.bytecodeUseReflectionOptimizer);
        if (StringUtils.isNotEmpty(this.ddlAuto)) {
            hibernateProperties.setProperty("hibernate.hbm2ddl.auto", this.ddlAuto);
        }
        hibernateProperties.setProperty("hibernate.show_sql", this.showSql);
        hibernateProperties.setProperty("hibernate.format_sql", this.formatSql);
        hibernateProperties.setProperty("hibernate.generate_statistics", this.generateStatistics);
        hibernateProperties.setProperty("hibernate.connection.autocommit", this.hibernateConnectionAutocommit);
        hibernateProperties.setProperty("hibernate.connection.release_mode", "on_close");
        sessionFactory.setHibernateProperties(hibernateProperties);

        return sessionFactory;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor getPersistenceExceptionTranslationPostProcessor() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    private Properties parseConnectionProperties() {
        Properties properties = new Properties();
        String[] propPair = this.connectionProperties.split("&");
        for (String prop : propPair) {
            int idx = prop.indexOf('=');
            if (idx > 0) {
                String name = prop.substring(0, idx);
                String value = prop.substring(idx + 1);
                properties.setProperty(name, value);
            } else {
                LOGGER.warn("Some connection properties will be ignored because format (<name1>=<value1>[&<name2>=<value2>]*) was not respected: {}",
                    this.connectionProperties);
                return null;
            }
        }
        return properties;
    }
}
