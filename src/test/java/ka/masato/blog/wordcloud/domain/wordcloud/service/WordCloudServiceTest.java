package ka.masato.blog.wordcloud.domain.wordcloud.service;

import ka.masato.blog.wordcloud.domain.wordcloud.helper.TokenizerHelper;
import ka.masato.blog.wordcloud.domain.wordcloud.helper.WordCloudCreateHelper;
import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.RawTextRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudMetaDataRepository;
import ka.masato.blog.wordcloud.domain.wordcloud.service.exception.MalformedMetaDataEntityException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


public class WordCloudServiceTest {

    @Mock
    RawTextRepository rawTextRepository;
    @Mock
    TokenizerHelper tokenizerHelper;
    @Mock
    WordCloudCreateHelper wordCloudCreateHelper;
    @Mock
    WordCloudImageRepository wordCloudImageRepository;
    @Mock
    WordCloudMetaDataRepository wordCloudMetaDataRepository;

    @InjectMocks
    private WordCloudService wordCloudService;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void registerMetaDataNormalTest() {
        WordCloudMetaData test = new WordCloudMetaData("REGISTERED");
        when(wordCloudMetaDataRepository.save(test)).thenReturn(test);
        WordCloudMetaData result = wordCloudService.registerMetaData(test);
        verify(wordCloudMetaDataRepository).save(test);
        assertThat(result.getStatusCode().getStatusCode(), is("REGISTERED"));
    }

    @Test
    public void registerMetaDataAbnormalTest01() {
        WordCloudMetaData test = new WordCloudMetaData("CREATED");
        when(wordCloudMetaDataRepository.save(test)).thenReturn(test);
        WordCloudMetaData result = wordCloudService.registerMetaData(test);
        verify(wordCloudMetaDataRepository).save(test);
        assertThat(result.getStatusCode().getStatusCode(),is("REGISTERED"));
    }

    @Test(expected = MalformedMetaDataEntityException.class)
    public void registerMetaDataAbnormalTest02() {
        WordCloudMetaData test = new WordCloudMetaData("CREATED");
        test.setWordCloudId(1);
        when(wordCloudMetaDataRepository.save(test)).thenReturn(test);
        WordCloudMetaData result = wordCloudService.registerMetaData(test);
    }

    @Test
    public void createWordCloudNormalTest() {
        String url = "http://example.com";
        WordCloudMetaData test = new WordCloudMetaData("REGISTER");
        test.setUrl(url);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.of(test));
        Optional<String> rawText = Optional.ofNullable("test string.");
        when(rawTextRepository.getRawText(url)).thenReturn(rawText);
        Map<String, Long> data = new HashMap<>();
        data.put("test",100L);
        when(tokenizerHelper.getTokenize(rawText.get())).thenReturn(data);
        BufferedImage bufferedImage = new BufferedImage(100,100, 1);
        when(wordCloudCreateHelper.setWidth(100)).thenReturn(wordCloudCreateHelper);
        when(wordCloudCreateHelper.setHeight(100)).thenReturn(wordCloudCreateHelper);
        when(wordCloudCreateHelper.setDataSet(data)).thenReturn(wordCloudCreateHelper);
        when(wordCloudCreateHelper.build()).thenReturn(bufferedImage);
        when(wordCloudImageRepository.saveImage(bufferedImage)).thenReturn("gcs/image/path");

        WordCloudMetaData result = wordCloudService
                    .createWordCloud(1, 100, 100, "circle");

        assertThat(result.getImagePath(), is("gcs/image/path"));
        assertThat(result.getStatusCode().getStatusCode(), is("CREATED"));

    }

    @Test
    public void updateWordCloud() {

    }

    @Test
    public void getWordCloudMetaDataNormalTest() {
        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.of(new WordCloudMetaData("CREATED")));
        WordCloudMetaData actual = wordCloudService.getWordCloudMetaData(1);
        verify(wordCloudMetaDataRepository).findById(1);
        assertThat(actual.getStatusCode().getStatusCode(), is("CREATED"));
    }

    @Test(expected = RuntimeException.class)
    public void getWordCloudMetaDataAbnormalTest() {
        when(wordCloudMetaDataRepository.findById(10)).thenReturn(Optional.empty());
        WordCloudMetaData actual = wordCloudService.getWordCloudMetaData(1);
        verify(wordCloudMetaDataRepository).findById(10);
        assertThat(actual.getStatusCode().getStatusCode(), is("CREATED"));
    }

    @Test
    public void deleteWordCloudMetaDataNormalTest() {
        String targetUrl = "http://example.com/hoge";
        String targetImagePath = "imagepath";
        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("CREATED");
        wordCloudMetaData.setUrl(targetUrl);
        wordCloudMetaData.setImagePath(targetImagePath);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.of(wordCloudMetaData));

        wordCloudService.deleteWordCloudMetaData(1);

        verify(wordCloudImageRepository).delete(targetImagePath);
        verify(wordCloudMetaDataRepository).delete(wordCloudMetaData);

    }

    @Test
    public void deleteWordCloudMetaDataNormalTest02() {
        String targetUrl = "";
        String targetImagePath = "imagepath";
        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("CREATED");
        wordCloudMetaData.setUrl(targetUrl);
        wordCloudMetaData.setImagePath(targetImagePath);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.of(wordCloudMetaData));

        wordCloudService.deleteWordCloudMetaData(1);

        verify(wordCloudImageRepository).delete(targetImagePath);
        verify(wordCloudMetaDataRepository).delete(wordCloudMetaData);

    }

    @Test(expected = RuntimeException.class)
    public void deleteWordCloudMetaDataAbnormalTest01() {
        String targetUrl = "http://example.com/test";
        String targetImagePath = "imagepath";
        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("CREATED");
        wordCloudMetaData.setUrl(targetUrl);
        wordCloudMetaData.setImagePath(targetImagePath);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.empty());

        wordCloudService.deleteWordCloudMetaData(1);

    }

    @Test(expected = RuntimeException.class)
    public void deleteWordCloudMetaDataAbnormalTest02() {
        String targetUrl = "http://example.com/test";
        String targetImagePath = "imagepath";
        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("CREATED");
        wordCloudMetaData.setUrl(targetUrl);
        wordCloudMetaData.setImagePath(targetImagePath);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.empty());

        wordCloudService.deleteWordCloudMetaData(1);
        //TODO Check log output.
        verify(wordCloudMetaDataRepository).delete(wordCloudMetaData);

    }

    @Test(expected = RuntimeException.class)
    public void deleteWordCloudMetaDataAbnormalTest03() {
        String targetUrl = "hogehoge";
        String targetImagePath = "malformedURL";
        WordCloudMetaData wordCloudMetaData = new WordCloudMetaData("CREATED");
        wordCloudMetaData.setUrl(targetUrl);
        wordCloudMetaData.setImagePath(targetImagePath);

        when(wordCloudMetaDataRepository.findById(1)).thenReturn(Optional.empty());

        wordCloudService.deleteWordCloudMetaData(1);
        //TODO Check log output.
        verify(wordCloudMetaDataRepository).delete(wordCloudMetaData);

    }

}