package ru.corruptzero.eleftheriaback.configuration;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

/**
 * Configuration class for setting up a database connection and entity manager.
 * <p>
 * The {@link LocalContainerEntityManagerFactoryBean} bean is used to create an entity manager factory. It is
 * configured with a {@link DataSource} and packages to scan for entities.
 * It is also configured with a {@link JpaVendorAdapter} and additional properties.
 * <p>
 * The {@link DataSource} bean is used to create a data source for the application.
 * It is configured with driver classname, URL, username, and password properties that are read from the {@link Environment} object.
 * <p>
 * The {@link #additionalProperties()} method creates a {@link Properties} object that contains additional properties
 * for the entity manager factory, such as the Hibernate dialect and the Hibernate DDL auto-generation setting. These
 * properties are also read from the {@link Environment} object.
 * <p>
 * The {@link EnableTransactionManagement} annotation enables Spring's annotation-driven transaction management capability.
 * <p>
 * The {@link EnableJpaRepositories} annotation enables the use of JPA repositories in the application. It specifies the
 * package where the repository interfaces are located.
 * <p>
 * The {@link PropertySource} annotation specifies the location of a property file that the application will use to
 * configure itself.
 *
 * <p>Copyright (c) 2023 corruptzero</p>
 * <p>Licensed under the MIT License.</p>
 *
 * @author corruptzero
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "ru.corruptzero.eleftheriaback.domain.repository")
@PropertySource("classpath:application.properties")
public class JpaConfig {

    @Autowired
    private Environment env;

    /**
     * Creates a {@link LocalContainerEntityManagerFactoryBean} bean that is used to create an entity manager factory.
     *
     * @return the entity manager factory bean
     */
    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean emf = new LocalContainerEntityManagerFactoryBean();
        emf.setDataSource(dataSource());
        emf.setPackagesToScan("ru.corruptzero.eleftheriaback.domain.entity");

        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        emf.setJpaVendorAdapter(vendorAdapter);
        emf.setJpaProperties(additionalProperties());
        return emf;
    }

    /**
     * Creates a {@link DataSource} bean that is used to create a data source for the application.
     *
     * @return the data source bean
     */
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("spring.datasource.driver-class-name")));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));
        return dataSource;
    }

    /**
     * Creates additional properties for the entity manager factory using properties from the {@link Environment} object.
     *
     * @return the additional properties for the entity manager factory
     */
    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
        properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
        return properties;
    }
}
