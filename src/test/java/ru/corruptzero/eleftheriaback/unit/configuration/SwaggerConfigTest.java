package ru.corruptzero.eleftheriaback.unit.configuration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.corruptzero.eleftheriaback.configuration.SwaggerConfig;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class SwaggerConfigTest {

    @Autowired
    SwaggerConfig swaggerConfig;

    @Test
    void testConfiguration() {
        Docket docket = swaggerConfig.api();
        assertNotNull(docket);
        assertEquals(DocumentationType.SWAGGER_2, docket.getDocumentationType());
    }
}
