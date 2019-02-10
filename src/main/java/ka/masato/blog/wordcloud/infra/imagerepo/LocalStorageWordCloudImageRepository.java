package ka.masato.blog.wordcloud.infra.imagerepo;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@Primary
@Profile("default")
@Repository
public class LocalStorageWordCloudImageRepository implements WordCloudImageRepository {

    private final String IMAGE_FORMAT = "png";


    private final String persistentRootPath;

    public LocalStorageWordCloudImageRepository(
            @Value("${wordcloud.persistent.root.path:.}")String persistentRootPath
    ) {
        this.persistentRootPath = persistentRootPath;
    }


    @Override
    public String saveImage(BufferedImage image) {

        Path saveImagePath = Paths.get(this.persistentRootPath, this.createImageFileName()+"."+IMAGE_FORMAT);
        try {
            OutputStream os = new FileOutputStream(saveImagePath.toString());
            ImageIO.write(image, IMAGE_FORMAT, os);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return saveImagePath.toString();
    }

    @Override
    public BufferedImage getImage(WordCloudMetaData imageMetadata) {
        return null;
    }

    @Override
    public void delete(String url) {

    }

    private String createImageFileName(){
        return RandomStringUtils.randomAlphanumeric(20);
    }

}
