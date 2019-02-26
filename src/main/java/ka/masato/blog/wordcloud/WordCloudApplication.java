package ka.masato.blog.wordcloud;

import com.atilika.kuromoji.ipadic.Tokenizer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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
        logger.info(Locale.getDefault().getLanguage());
        logger.info(Locale.getDefault().getDisplayLanguage());
        Locale.setDefault(Locale.JAPANESE);
        logger.info(Locale.getDefault().getLanguage());
        logger.info(Locale.getDefault().getDisplayLanguage());
        Map<String, String> env = System.getenv();

        logger.info(env.get("JRE_LIB_FONTS") + "/" + "kochi-gothic-subst.ttf");
        File font = new File(env.get("JRE_LIB_FONTS") + "/" + "kochi-gothic-subst.ttf");
        if (font.exists()) {
            logger.info("Exist!");
        }else{logger.info("No!");}
    //        Properties hoge = System.getProperties();
//        hoge.keySet().stream().forEach(System.out::println);
//        logger.info("USER.DIR:" + hoge.getProperty("user.dir"));
//        String fontConfig = System.getProperty("java.home")
//                + File.separator + "lib"
//                + File.separator + "fontconfig.Prodimage.properties";
//        logger.info(fontConfig);
//        if (new File(fontConfig).exists()){
//            System.setProperty("sun.awt.fontconfig", fontConfig);
//            Files.lines(Paths.get(fontConfig), StandardCharsets.UTF_8)
//                    .forEach(System.out::println);
//
//        }
//

    }
}

