package ka.masato.blog.wordcloud.infra;

import com.atilika.kuromoji.ipadic.Token;
import com.atilika.kuromoji.ipadic.Tokenizer;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class UrlTokenizer {

    private Tokenizer tokenizer;

    public UrlTokenizer(Tokenizer tokenizer) {
        this.tokenizer = tokenizer;
    }


    public Map<String ,Long> scraip(String abstUrl) throws IOException {
        URL url = new URL(abstUrl);
        String result = Jsoup.parse(url, 10000).text();
        List<Token> tokens = tokenizer.tokenize(result);
        Map<String,Long> dataSet = tokens.stream()
                .filter(t -> t.getPartOfSpeechLevel2().equals("固有名詞"))
                .map(t -> t.getSurface())
                .collect(Collectors.groupingBy(e->e,Collectors.counting()));
        return dataSet;
    }


}
