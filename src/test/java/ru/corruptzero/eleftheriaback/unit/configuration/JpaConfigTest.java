package ru.corruptzero.eleftheriaback.unit.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import ru.corruptzero.eleftheriaback.configuration.JpaConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
class JpaConfigTest {

    @Autowired
    private JpaConfig jpaConfig;

    @Autowired
    private LocalContainerEntityManagerFactoryBean entityManagerFactory;

    @Autowired
    private DataSource dataSource;

    @Test
    void testConfiguration() {
        assertNotNull(jpaConfig);
        assertNotNull(entityManagerFactory);
        assertNotNull(dataSource);
    }
}

