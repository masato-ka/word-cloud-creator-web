package ka.masato.blog.wordcloud.infra.imagerepo;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import org.springframework.stereotype.Repository;

import java.awt.image.BufferedImage;

@Repository
public class GcsWordCloudImageRepository implements WordCloudImageRepository {

    @Override
    public String saveImage(BufferedImage image) {
        return null;
    }

    @Override
    public BufferedImage getImage(WordCloudMetaData imageMetadata) {
        return null;
    }

    @Override
    public void delete(String url) {

    }
}
