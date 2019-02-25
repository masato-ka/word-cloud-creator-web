package ka.masato.blog.wordcloud.domain.wordcloud.helper;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.exception.KumoException;
import com.kennycason.kumo.font.FontWeight;
import com.kennycason.kumo.font.KumoFont;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WordCloudCreateHelper {


    private static final Logger logger = LoggerFactory.getLogger(WordCloudCreateHelper.class);
    private int width = 640;
    private int height = 420;
    private List<WordFrequency> wordFrequencies;
    private KumoFont kumoFont;
    private ResourceLoader resourceLoader;


    public WordCloudCreateHelper(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
        Resource resource = this
                .resourceLoader
                .getResource("classpath:" + "static/font/NotoSansJP-Bold.otf");
        InputStream is = null;
        try {

            logger.info("Start load embeded font.");
            is = resource.getInputStream();
            is.close();
            logger.info("resource info:" + resource.toString());
            //this.kumoFont = new KumoFont(is);
            this.kumoFont = new KumoFont(Font.SANS_SERIF, FontWeight.BOLD);

        } catch (KumoException | IOException e) {
            System.out.println(e.getMessage());
            logger.warn("Failed loading embeded font :" + e.getMessage());
            throw new KumoException(e.getMessage());
        }
    }


    public WordCloudCreateHelper setHeight(int height) {
        this.height = height;
        return this;
    }

    public WordCloudCreateHelper setWidth(int width) {
        this.width = width;
        return this;
    }

    public WordCloudCreateHelper setDataSet(Map<String, Long> dataSet){
        wordFrequencies = dataSet.entrySet()
                .stream()
                .map(data -> new WordFrequency(data.getKey(), data.getValue().intValue()))
                .collect(Collectors.toList());
        return this;
    }

    public BufferedImage build() {

        if (wordFrequencies == null) {
            throw new IllegalArgumentException("Should be set Dataset before build.");
        }

        final Dimension dimension = new Dimension(this.width, this.height);
        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.PIXEL_PERFECT);
        wordCloud.setKumoFont(kumoFont);
        wordCloud.setPadding(2);
        wordCloud.setBackground(new CircleBackground(200));
        wordCloud.setBackgroundColor(Color.white);
        wordCloud.setColorPalette(new LinearGradientColorPalette(Color.RED, Color.BLUE, Color.GREEN,
                30, 30));
        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
        wordCloud.build(this.wordFrequencies);
        return wordCloud.getBufferedImage();
    }


}
