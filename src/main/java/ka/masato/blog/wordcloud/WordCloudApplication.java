package ka.masato.blog.wordcloud;

import com.atilika.kuromoji.ipadic.Tokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WordCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(WordCloudApplication.class, args);
    }

    @Autowired
    Tokenizer tokenizeService;

    @Bean
    public Tokenizer getTokenizer(){
        return new Tokenizer();
    }

    @Bean
    public RestTemplate restTemplate(){
        RestTemplate restTemplate = new RestTemplate();
        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
        StringHttpMessageConverter stringHttpMessageConverter = new StringHttpMessageConverter();
        stringHttpMessageConverter.setDefaultCharset(Charset.forName("UTF-8"));
        messageConverters.add(stringHttpMessageConverter);
        restTemplate.setMessageConverters(messageConverters);
        return restTemplate;
    }

}

