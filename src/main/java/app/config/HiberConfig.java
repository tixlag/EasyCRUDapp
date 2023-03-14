package app.config;


import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Properties;


@Configuration
@PropertySource("classpath:db.properties")
@EnableTransactionManagement
@ComponentScan("app") // ?? Как указать, чтобы в директории java искал?
public class HiberConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        // добавили в датасорс свойства транзакций
        dataSource.setInitialSize(Integer.valueOf(env.getRequiredProperty("db.initialSize")));
        dataSource.setMinIdle(Integer.valueOf(env.getRequiredProperty("db.minIdle")));
        dataSource.setMaxIdle(Integer.valueOf(env.getRequiredProperty("db.maxIdle")));
        dataSource.setTimeBetweenEvictionRunsMillis(Long.valueOf(env.getRequiredProperty("db.timeBetweenEvictionRunsMillis")));
        dataSource.setMinEvictableIdleTimeMillis(Long.valueOf(env.getRequiredProperty("db.minEvictableIdleTimeMillis")));
        dataSource.setTestOnBorrow(Boolean.valueOf(env.getRequiredProperty("db.testOnBorrow")));
        dataSource.setValidationQuery(env.getRequiredProperty("db.validationQuery"));
        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean getEntityManager() {
        LocalContainerEntityManagerFactoryBean entityManager = new LocalContainerEntityManagerFactoryBean();

        entityManager.setDataSource(getDataSource());
        entityManager.setPackagesToScan("app");
        entityManager.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManager.setJpaProperties(getHibernateProperties());

        return entityManager;
    }

    @Bean
    public PlatformTransactionManager getTransactionManager() {
        JpaTransactionManager manager = new JpaTransactionManager();
        manager.setEntityManagerFactory(getEntityManager().getObject());
        return manager;
    }

    public Properties getHibernateProperties() {
        Properties props = new Properties();
        props.put("hibernate.show_sql", env.getProperty("hibernate.show_sql"));
        props.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        return props;
    }

//   @Bean
//   @Scope("prototype")
//   public Car getCar(String model, int series) {
//
//   }
}
