package ka.masato.blog.wordcloud.infra.rawtext;

import ka.masato.blog.wordcloud.domain.wordcloud.repository.RawTextRepository;
import ka.masato.blog.wordcloud.infra.rawtext.exception.MalformedTargetAddressException;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.RestTemplate;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Optional;

@Repository
public class RestTemplateRawTextRepository implements RawTextRepository {

    private RestTemplate restTemplate;

    public RestTemplateRawTextRepository(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    @Override
    public Optional<String> getRawText(String urlString) {
        String result = null;

        try {
            URL url = new URL(urlString);
        } catch (MalformedURLException e) {
            throw new MalformedTargetAddressException("Malformed target URI: " + e.getMessage());
        }

        try {
            URI uri = new URI(urlString);
            result = restTemplate.getForObject(uri, String.class);
        } catch (URISyntaxException e) {
            throw new MalformedTargetAddressException("Malformed target URI: " + e.getMessage());
        }

        return Optional.ofNullable(result);

    }
}
