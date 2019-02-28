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

        String fontPropertiesFilePath = "/tmp/fontconfig.properties";
        String fontFilePath = "/tmp/NotoSansJP-Regular.otf";
        String fontBoldFilePath = "/tmp/NotoSansJP-Bold.otf";

        String fontFileResourcePath = "static/font/NotoSansJP-Regular.otf";
        String fontBoldFileResourcePath = "static/font/NotoSansJP-Bold.otf";
        String fontPropertiesResourcePath = "static/font/fontconfig.properties.template";


        Resource fontFileResource = resourceLoader.getResource("classpath:" + fontFileResourcePath);
        Resource fontFileBoldResource = resourceLoader.getResource("classpath:" + fontBoldFileResourcePath);
        Resource fontPropertiesResource = resourceLoader.getResource("classpath:"+fontPropertiesResourcePath);

        File fontFile = new File(fontFilePath);
        File fontBoldFile = new File(fontBoldFilePath);
        File fontPropertiesFile = new File(fontPropertiesFilePath);

        if (fontPropertiesFile.exists()) {
        }else{
            Files.copy(fontFileBoldResource.getInputStream(), fontBoldFile.toPath());
            Files.copy(fontFileResource.getInputStream(), fontFile.toPath());
            Files.copy(fontPropertiesResource.getInputStream(), fontPropertiesFile.toPath());

        }

        System.setProperty("sun.awt.fontconfig", fontFilePath);
    }


//    private void deployFontSettings(){
//        String resourcePathPrefix = "static" + File.pathSeparator + "font" + File.pathSeparator;
//        String filePathPrefix = "/tmp" + File.pathSeparator;
//        String[] fileNames = {"fontconfig.properties", "NotoSansJP-Regular.otf", "NotoSansJP-Bold.otf"};
//        Arrays.stream(fileNames)
//                .map(fileName -> resourceLoader.getResource("classpath:" + resourcePathPrefix + fileName))
//                .map(resource -> Files.copy(resource.getInputStream(),
//                        new File(filePathPrefix + resource.getFilename())));
//
//    }

}

