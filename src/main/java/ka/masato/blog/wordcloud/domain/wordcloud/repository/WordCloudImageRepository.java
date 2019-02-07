package ka.masato.blog.wordcloud.domain.wordcloud.repository;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;

@Repository
public interface WordCloudImageRepository {

    String saveImage(BufferedImage image);
    BufferedImage getImage(WordCloudMetaData imageMetadata);
    void delete(String url);
}
