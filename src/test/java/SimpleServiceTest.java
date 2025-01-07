import com.ccat.annotation.AnnotationData;
import com.ccat.response.Response;
import com.ccat.service.ServiceFactory;
import com.ccat.service.SimpleRequest;
import com.ccat.service.SimpleService;
import com.ccat.util.ApiHttpClient;
import com.ccat.util.CGsonHandler;
import com.ccat.util.HttpRequestBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import util.ResponseBuilder;

import java.net.URI;
import java.net.http.HttpRequest;
import java.util.Map;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.mockStatic;

public class SimpleServiceTest {
    private final Logger logger = LoggerFactory.getLogger(SimpleServiceTest.class);

    private final String BASEURL = "https://api.cc4t.com/";
    private SimpleService service;
    private final Map<String,String> headers = Map.of("Content-Type","application/json");

    @BeforeEach
    void setUp() {
        service = new ServiceFactory(BASEURL).createService(headers, SimpleService.class);
    }

    @Test
    void test_getBasicResponseOverProxy_returnStringResponse() {
        //given
        URI uri = URI.create(BASEURL + "posts/1");
        AnnotationData annotationData = new AnnotationData("GET", uri, null);
        HttpRequest request = HttpRequestBuilder.build(
                headers, annotationData.httpMethod(),annotationData.uriTemplate(),null);

        String responseBody = "TEST";
        int responseCode = 200;

        try (MockedStatic<ApiHttpClient> mock = mockStatic(ApiHttpClient.class)) {
            mock.when(() ->
                    ApiHttpClient.getHttpResponse(request)
            ).thenReturn(
                    new ResponseBuilder(responseCode,request,request.headers(),responseBody,uri)
            );

            // when
            Response<String> response = service.getResponse().execute();
            String body = response.body();

            logger.debug(response.toString());

            // then
            assertThat(body).isNotNull();
            assertThat(response.statusCode()).isEqualTo(responseCode);
            assertThat(response.body()).isEqualTo(responseBody);
        }
    }

    @Test
    void test_postCreateResourceOverProxy_returnStringResponse() {
        //given
        URI uri = URI.create(BASEURL + "posts");

        SimpleRequest requestBody = new SimpleRequest("abc");
        String body = CGsonHandler.getInstance().toJson(requestBody, SimpleRequest.class);

        HttpRequest request = HttpRequestBuilder.build(
                headers,"POST",uri,body);

        String expectedBody = "TEST";
        int responseCode = 200;

        try (MockedStatic<ApiHttpClient> mock = mockStatic(ApiHttpClient.class)) {
            mock.when(() ->
                    ApiHttpClient.getHttpResponse(request)
            ).thenReturn(
                    new ResponseBuilder(responseCode,request,request.headers(),expectedBody,uri)
            );

            // when
            Response<String> response = service.createPost(requestBody).execute();
            String returnedBody = response.body();

            logger.debug(response.toString());

            // then
            assertThat(returnedBody).isNotNull();
            assertThat(response.statusCode()).isEqualTo(responseCode);
            assertThat(response.body()).isEqualTo(expectedBody);
        }
    }
}
