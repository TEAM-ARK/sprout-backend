package team.ark.sprout.config;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@TestPropertySource(value = "file:${user.home}/team-ark-backend/sprout-database-prod.properties")
class ProdDatabasePropertiesTest {
    @Autowired
    DatabaseProperties properties;

//    @Test
//    @DisplayName("데이터베이스 개발환경")
//    @IfProfileValue(name = "spring.profiles.active", value = "dev")
//    void ifDev() throws Exception {
//        assertThat(properties.getUrl()).isEqualTo("jdbc:h2:mem:test");
//    }

    @Test
    @DisplayName("데이터베이스 운영환경")
    void ifProd() throws Exception {
        assertThat(properties.getUrl()).startsWith("jdbc:mysql:");
    }
}
