import com.ccat.annotation.AnnotationData;
import com.ccat.annotation.AnnotationParser;
import com.ccat.service.SimpleRequest;
import com.ccat.service.SimpleService;
import com.ccat.util.GsonHandler;
import com.ccat.util.HttpRequestBuilder;
import org.junit.jupiter.api.Test;
import util.FlowSubscriber;

import java.lang.reflect.Method;
import java.net.URI;
import java.net.http.HttpRequest;
import java.nio.ByteBuffer;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class AnnotationParserTest {
    private final String BASEURL = "https://api.cc4t.com/";

    @Test
    public void test_getRequest_returnGetRequestObject() throws NoSuchMethodException {
        //given
        Method method = SimpleService.class.getMethod("getResponse");
        Object[] args = {"posts/1"};
        String auth = "123";

        AnnotationData annotationData = AnnotationParser.parseAnnotationData(method, BASEURL, args);

        //when
        HttpRequest request = HttpRequestBuilder.build(auth,
                annotationData.httpMethod(),annotationData.uriTemplate(),annotationData.requestBody().orElse(null));

        //then
        assertThat(request.headers().firstValue("Authorization")).isNotNull();
        assertThat(request.headers().firstValue("Authorization").get()).isEqualTo(auth);

        assertThat(request.method()).isEqualTo("GET");
        assertThat(request.uri()).isEqualTo(URI.create(BASEURL + args[0]));
    }

    @Test
    public void test_postRequest_returnPostRequestObjectWithBody() throws NoSuchMethodException {
        //given
        Method method = SimpleService.class.getMethod("createPost", SimpleRequest.class);
        String body = "TEST_BODY";
        SimpleRequest req = new SimpleRequest(body);
        String expected = GsonHandler.getInstance().serialize(req);

        Object[] args = {"posts", req};
        String auth = "123";

        AnnotationData annotationData = AnnotationParser.parseAnnotationData(method, BASEURL, args);

        //when
        HttpRequest request = HttpRequestBuilder.build(auth,
                annotationData.httpMethod(),annotationData.uriTemplate(),annotationData.requestBody().orElse(null));

        //then
        assertThat(request.headers().firstValue("Authorization")).isNotNull();
        assertThat(request.headers().firstValue("Authorization").get()).isEqualTo(auth);

        assertThat(request.method()).isEqualTo("POST");
        assertThat(request.uri()).isEqualTo(URI.create(BASEURL + args[0]));

        assertThat(request.bodyPublisher()).isPresent();
        FlowSubscriber<ByteBuffer> flowSubscriber = new FlowSubscriber<>();
        request.bodyPublisher().get().subscribe(flowSubscriber);

        byte[] actual = flowSubscriber.getBodyItems().getFirst().array();
        assertThat(actual).isEqualTo(expected.getBytes());
    }

    @Test
    public void test_updateRequest_returnPostRequestObjectWithBody() throws NoSuchMethodException {
        //given
        Method method = SimpleService.class.getMethod("createPost", SimpleRequest.class);
        String body = "TEST_BODY";
        SimpleRequest req = new SimpleRequest(body);
        String expected = GsonHandler.getInstance().serialize(req);

        Object[] args = {"posts", req};
        String auth = "123";

        AnnotationData annotationData = AnnotationParser.parseAnnotationData(method, BASEURL, args);

        //when
        HttpRequest request = HttpRequestBuilder.build(auth,
                annotationData.httpMethod(),annotationData.uriTemplate(),annotationData.requestBody().orElse(null));

        //then
        assertThat(request.headers().firstValue("Authorization")).isNotNull();
        assertThat(request.headers().firstValue("Authorization").get()).isEqualTo(auth);

        assertThat(request.method()).isEqualTo("POST");
        assertThat(request.uri()).isEqualTo(URI.create(BASEURL + args[0]));

        assertThat(request.bodyPublisher()).isPresent();
        FlowSubscriber<ByteBuffer> flowSubscriber = new FlowSubscriber<>();
        request.bodyPublisher().get().subscribe(flowSubscriber);

        byte[] actual = flowSubscriber.getBodyItems().getFirst().array();
        assertThat(actual).isEqualTo(expected.getBytes());
    }
}
