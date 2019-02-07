package ka.masato.blog.wordcloud.domain.wordcloud.helper;

import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.CircleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.palette.LinearGradientColorPalette;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class WordCloudCreateHelper {

    private int width = 640;
    private int height = 420;
    private List<WordFrequency> wordFrequencies;


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
