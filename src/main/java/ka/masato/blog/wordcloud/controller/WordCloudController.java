package ka.masato.blog.wordcloud.controller;

import ka.masato.blog.wordcloud.controller.model.WordCloudModel;
import ka.masato.blog.wordcloud.infra.UrlTokenizer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WordCloudController {

    private UrlTokenizer tokenizeService;

    @GetMapping
    public String getWordCloud(Model model){
        WordCloudModel wordCloudModel = new WordCloudModel();

        //TODO scraip
        //TODO change model


        model.addAttribute("wordCloudData", wordCloudModel);
        return "wordcloud";
    }

}
