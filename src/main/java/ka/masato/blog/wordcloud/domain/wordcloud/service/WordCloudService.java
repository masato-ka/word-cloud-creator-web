package ka.masato.blog.wordcloud.domain.wordcloud.service;
import ka.masato.blog.wordcloud.domain.wordcloud.model.StatusCode;
import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.RawTextRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.helper.TokenizerHelper;
import ka.masato.blog.wordcloud.domain.wordcloud.helper.WordCloudCreateHelper;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudMetaDataRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.service.exception.MalformedMetaDataEntityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.awt.image.BufferedImage;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;

@Service
public class WordCloudService {

    private static final Logger logger = LoggerFactory.getLogger(WordCloudService.class);

    private RawTextRepository rawTextRepository;
    private TokenizerHelper tokenizerHelper;
    private WordCloudCreateHelper wordCloudCreateHelper;
    private WordCloudImageRepository wordCloudImageRepository;
    private WordCloudMetaDataRepository wordCloudMetaDataRepository;

    public WordCloudService(RawTextRepository rawTextRepository,
                            TokenizerHelper tokenizerHelper,
                            WordCloudCreateHelper wordCloudCreateHelper,
                            WordCloudImageRepository wordCloudImageRepository,
                            WordCloudMetaDataRepository wordCloudMetaDataRepository) {

        this.rawTextRepository = rawTextRepository;
        this.tokenizerHelper = tokenizerHelper;
        this.wordCloudCreateHelper = wordCloudCreateHelper;

        this.wordCloudImageRepository = wordCloudImageRepository;
        this.wordCloudMetaDataRepository = wordCloudMetaDataRepository;

    }

    // This class create Image. and return MetaData.
    @Transactional
    public WordCloudMetaData registerMetaData(WordCloudMetaData wordCloudMetaData){
        if (wordCloudMetaData.getWordCloudId() != null) {
            throw new MalformedMetaDataEntityException("Do not set meta data Id before register.");
        }
        wordCloudMetaData.setStatusCode(new StatusCode("REGISTERED"));
        return wordCloudMetaDataRepository.save(wordCloudMetaData);
    }

    @Transactional
    public WordCloudMetaData createWordCloud(Integer metaDataId, int width, int height, String type){
        Optional<WordCloudMetaData> result = wordCloudMetaDataRepository.findById(metaDataId);
        WordCloudMetaData wordCloudMetaData =  result.orElseThrow(()->new RuntimeException());
        wordCloudMetaData.updateWordCloudSetting(type, width, height);
        wordCloudMetaData.setStatusCode(new StatusCode("CREATED"));

        String imagePath = createImage(width, height, wordCloudMetaData.getUrl());

        wordCloudMetaData.setImagePath(imagePath);
        wordCloudMetaData.setAtCreated(LocalDateTime.now());
        return wordCloudMetaData;
    }

    @Transactional
    public WordCloudMetaData updateWordCloud(Integer metaDataId, int width, int height, String type){
        Optional<WordCloudMetaData> result = wordCloudMetaDataRepository.findById(metaDataId);
        WordCloudMetaData wordCloudMetaData = result.orElseThrow(() -> new RuntimeException());

        wordCloudMetaData.updateWordCloudSetting(type, width, height);
        wordCloudMetaData.setStatusCode(new StatusCode("CREATED"));

        String imagePath = createImage(width, height, wordCloudMetaData.getUrl());
        wordCloudImageRepository.delete(wordCloudMetaData.getImagePath());
        wordCloudMetaData.setImagePath(imagePath);
        wordCloudMetaData.setAtLastUpdate(LocalDateTime.now());
        return wordCloudMetaData;
    }

    @Transactional
    public WordCloudMetaData getWordCloudMetaData(Integer id){
        Optional<WordCloudMetaData> wordCloudMetaData = wordCloudMetaDataRepository.findById(id);
        return wordCloudMetaData.orElseThrow(()->new RuntimeException());
    }

    @Transactional
    public void deleteWordCloudMetaData(Integer id){
        Optional<WordCloudMetaData> wordCloudMetaData = wordCloudMetaDataRepository.findById(id);
        //TODO Please considere exception.
        WordCloudMetaData result = wordCloudMetaData.orElseThrow(() -> new RuntimeException());
        wordCloudImageRepository.delete(result.getImagePath());
        wordCloudMetaDataRepository.delete(result);
    }

    private String createImage(int width, int height, String url) {
        String rawText = rawTextRepository.getRawText(url);
        //TODO this code is spended sec order time.
        Map<String, Long> dataSet = tokenizerHelper.getTokenize(rawText);
        BufferedImage resultImage = wordCloudCreateHelper.setWidth(width).setHeight(height).setDataSet(dataSet).build();
        return wordCloudImageRepository.saveImage(resultImage);
    }

}
