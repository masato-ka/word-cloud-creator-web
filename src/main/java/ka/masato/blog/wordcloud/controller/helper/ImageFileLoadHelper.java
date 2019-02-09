package ka.masato.blog.wordcloud.controller.helper;

import ka.masato.blog.wordcloud.controller.helper.exception.NoSuchWordCloudImageException;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;
import java.util.Optional;

@Component
public class ImageFileLoadHelper {

    private Optional<byte[]> image;
    private static final String IMAGE_FORMAT = "png";

    public ImageFileLoadHelper getImageWithFilePath(String imageFilePath){

        try {
            BufferedImage image = ImageIO.read(new File(imageFilePath));
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(image, IMAGE_FORMAT, baos);
            this.image = Optional.ofNullable(baos.toByteArray());
        } catch (IOException e) {
            throw new NoSuchWordCloudImageException("The word cloud image file is not exist.");
        }

        return this;
    }

    public Optional<String> base64encode(){
        Optional<String> result = this.image
                .map(imageByte -> Base64.getEncoder().encodeToString(imageByte));
        return result;
    }

}
