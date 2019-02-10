package ka.masato.blog.wordcloud.controller;

import ka.masato.blog.wordcloud.controller.helper.ImageFileLoadHelper;
import ka.masato.blog.wordcloud.controller.model.WordCloudForm;
import ka.masato.blog.wordcloud.controller.model.WordCloudModel;
import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.service.WordCloudService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
public class WordCloudController {


    private WordCloudService wordCloudService;
    private ImageFileLoadHelper imageFileLoadHelper;

    public WordCloudController(WordCloudService wordCloudService, ImageFileLoadHelper imageFileLoadHelper) {
        this.wordCloudService = wordCloudService;
        this.imageFileLoadHelper = imageFileLoadHelper;
    }

    @GetMapping("wordcloud")
    public String getWordCloud(Model model){
        WordCloudModel wordCloudModel = new WordCloudModel();
        model.addAttribute("wordCloudData", wordCloudModel);

        return "wordcloud";
    }

    @PostMapping("wordcloud")
    public String postWordCloud(@ModelAttribute WordCloudForm wordCloudForm, Model model){

        String url = wordCloudForm.getUrl();

        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("REGISTERED");
        wordCloudMetaData.setUrl(url);
        wordCloudMetaData = wordCloudService.registerMetaData(wordCloudMetaData);
        wordCloudMetaData = wordCloudService.createWordCloud(wordCloudMetaData.getWordCloudId(),400,400,"circle");


        String imagePath = wordCloudMetaData.getImagePath();

        if (imagePath.startsWith("http")) {
            model.addAttribute("image", imagePath);
        }else {
            Optional<String> image = imageFileLoadHelper.getImageWithFilePath(wordCloudMetaData.getImagePath()).base64encode();
            image.map(i -> model.addAttribute("image", "data:image/png;base64," + i));
        }

        return "wordcloud";
    }

}
