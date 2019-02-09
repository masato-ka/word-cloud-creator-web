package ka.masato.blog.wordcloud.controller;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.service.WordCloudService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WordCloudRestController {

    private WordCloudService wordCloudService;


    public WordCloudRestController(WordCloudService wordCloudService) {
        this.wordCloudService = wordCloudService;
    }

    @RequestMapping("test")
    public String runtest(){

        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("REGISTERE");
        wordCloudMetaData.setUrl("https://yamama48.hatenablog.com/entry/american201901");
        wordCloudMetaData = wordCloudService.registerMetaData(wordCloudMetaData);
        wordCloudMetaData = wordCloudService
                .createWordCloud(wordCloudMetaData.getWordCloudId(), 600, 400, "circle");

        return wordCloudMetaData.getImagePath();

    }

}
