package ka.masato.blog.wordcloud.infra.imagerepo;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

@Repository
public class LocalStorageWordCloudImageRepository implements WordCloudImageRepository {


    private String format = "png";

    @Override
    public String saveImage(BufferedImage image) {

        String saveImagePath = "./test.png";
        try {
            OutputStream os = new FileOutputStream(saveImagePath);
            ImageIO.write(image,format, os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveImagePath;
    }

    @Override
    public BufferedImage getImage(WordCloudMetaData imageMetadata) {
        return null;
    }

    @Override
    public void delete(String url) {

    }
}
