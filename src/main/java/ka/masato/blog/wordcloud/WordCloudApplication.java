package ka.masato.blog.wordcloud;

import com.atilika.kuromoji.ipadic.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class WordCloudApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(WordCloudApplication.class);
    @Autowired
    private ResourceLoader resourceLoader;

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

    @Override
    public void run(String... args) throws Exception {
        Resource fontFileResource = resourceLoader.getResource("classpath:"+"static/font/NotoSansJP-Bold.otf");
        Resource fontPropertiesResource = resourceLoader.getResource("classpath:"+"static/font/fontconfig.properties.template");
        File fontFile = new File("/tmp/NotoSansJP-Bold.otf");
        File fontPropertiesFile = new File("/tmp/fontconfig.properties");
        if (fontPropertiesFile.exists()) {
        }else{
            Files.copy(fontFileResource.getInputStream(), fontFile.toPath());
            Files.copy(fontPropertiesResource.getInputStream(), fontPropertiesFile.toPath());
        }
        String fontConfig = "/tmp" + File.separator + "fontconfig.properties";
        logger.info(fontConfig);
        if (new File(fontConfig).exists()){
            System.setProperty("sun.awt.fontconfig", fontConfig);
        } else {
            logger.warn("can not find fontconfig.");

        }
    }
}

