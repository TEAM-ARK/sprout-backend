package team.ark.sprout.adapter.http;

import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static team.ark.sprout.adapter.http.ControllerTests.HOST;
import static team.ark.sprout.adapter.http.ControllerTests.SCHEME;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.restdocs.operation.preprocess.OperationRequestPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationResponsePreprocessor;
import org.springframework.restdocs.operation.preprocess.Preprocessors;
import org.springframework.restdocs.snippet.Attributes;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.ContentResultMatchers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.result.StatusResultMatchers;

@WebMvcTest(controllers = {
    AccountApiController.class
})
@Import(HttpEncodingAutoConfiguration.class)
@ExtendWith(RestDocumentationExtension.class)
@AutoConfigureRestDocs(uriScheme = SCHEME, uriHost = HOST)
public class ControllerTests {
    public static final String SCHEME = "https";
    public static final String HOST = "ark.sprout.io";
    public static final String ATTRIBUTE_FORMAT = "format";

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    protected static OperationRequestPreprocessor documentRequest() {
        return Preprocessors.preprocessRequest(
            Preprocessors.modifyUris()
                .scheme(SCHEME)
                .host(HOST)
                .removePort(),
            prettyPrint());
    }

    protected static OperationResponsePreprocessor documentResponse() {
        return Preprocessors.preprocessResponse(prettyPrint());
    }

    protected static StatusResultMatchers status() {
        return MockMvcResultMatchers.status();
    }

    protected static ContentResultMatchers content() {
        return MockMvcResultMatchers.content();
    }

    protected static Attributes.Attribute getFormatAttribute(String value) {
        return new Attributes.Attribute(ATTRIBUTE_FORMAT, value);
    }
}
