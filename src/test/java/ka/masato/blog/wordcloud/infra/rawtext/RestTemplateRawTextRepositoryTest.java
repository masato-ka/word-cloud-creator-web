package ka.masato.blog.wordcloud.infra.rawtext;

import ka.masato.blog.wordcloud.infra.rawtext.exception.MalformedTargetAddressException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withSuccess;

/*@TestExecutionListeners(
        DependencyInjectionTestExecutionListener.class,
        DirtiesContextTestExecutionListener.class,
        TransactionDbUnitTestExecutionListener.class
)*/
@RunWith(SpringRunner.class)
@SpringBootTest
public class RestTemplateRawTextRepositoryTest {

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private RestTemplateRawTextRepository restTemplateRawTextRepository;



    @Test
    public void getRawText() throws URISyntaxException {
        String testUrl = "http://fhdouafkla.com";
        String responseBody = "<HTML><head></head><body>今日はラーメンが食べたいラーメン。</body></HTML>";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(testUrl))
                .andRespond(withSuccess(responseBody, MediaType.TEXT_HTML));
        Optional<String> result = restTemplateRawTextRepository.getRawText(testUrl);

        assertThat(responseBody, is(result.get()));

    }


    @Test(expected = MalformedTargetAddressException.class)
    public void getRawTextAbnormal01(){
        String testUrl = "";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(testUrl)).andRespond(withSuccess("failed", MediaType.TEXT_HTML));
        Optional<String> result = restTemplateRawTextRepository.getRawText(testUrl);
    }

    @Test(expected = MalformedTargetAddressException.class)
    public void getRawTextAbnormal02(){
        String testUrl = "http://";
        MockRestServiceServer mockRestServiceServer = MockRestServiceServer.createServer(restTemplate);
        mockRestServiceServer.expect(requestTo(testUrl)).andRespond(withSuccess("failed", MediaType.TEXT_HTML));
        Optional<String> result = restTemplateRawTextRepository.getRawText(testUrl);
    }


}