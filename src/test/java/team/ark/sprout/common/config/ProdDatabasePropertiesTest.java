package team.ark.sprout.common.config;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import team.ark.sprout.common.config.DatabaseProperties;

@DisplayName("데이터베이스 - 운영")
@SpringBootTest(classes = DatabaseProperties.class)
@TestPropertySource("file:${user.home}/team-ark-backend/sprout-database-prod.properties")
class ProdDatabasePropertiesTest {
    @Autowired
    DatabaseProperties properties;

    @Test
    @DisplayName("데이터베이스 운영환경")
    void ifProd() throws Exception {
        assertThat(properties.getUrl()).startsWith("jdbc:mysql:");
        assertThat(properties.getUsername()).isEqualTo("ark-user");
    }
}
