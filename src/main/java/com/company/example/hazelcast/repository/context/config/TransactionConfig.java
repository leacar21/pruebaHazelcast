package com.company.example.hazelcast.repository.context.config;

import java.beans.PropertyVetoException;

import org.aspectj.lang.annotation.Pointcut;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.ImportResource;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;


@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableTransactionManagement(mode = AdviceMode.PROXY, proxyTargetClass = true)
@ImportResource("classpath:tx-config.xml")
public class TransactionConfig
    implements TransactionManagementConfigurer {

    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionConfig.class);

    @Autowired
    private DatabaseConfig database;

    @Bean(name = "transactionManager")
    public HibernateTransactionManager getHibernateTransactionManager() throws PropertyVetoException {
        LocalSessionFactoryBean factory = this.database.getLocalSessionFactoryBean();
        SessionFactory sessionFactory = factory.getObject();
        HibernateTransactionManager transactionManager = new HibernateTransactionManager(sessionFactory);
        return transactionManager;
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        try {
            return this.getHibernateTransactionManager();
        } catch (PropertyVetoException e) {
            LOGGER.error("Error configuring transaction manager");
            throw new RuntimeException(e);
        }
    }

    @Pointcut("execution(* com.company.example.hazelcast..*.external..*.*(..))")
    public void external() {
    }

    @Pointcut("execution(* com.company.example.hazelcast..*.internal..*.*(..))")
    public void internal() {
    }

    @Pointcut("execution(* com.company.example.hazelcast..*.integration..*.*(..))")
    public void integration() {
    }

    @Pointcut("execution(* com.company.example.hazelcast..*.support..*.*(..))")
    public void support() {
    }

    @Pointcut("execution(* com.company.example.hazelcast.repository.dao..*.*(..))")
    public void dao() {
    }
}
