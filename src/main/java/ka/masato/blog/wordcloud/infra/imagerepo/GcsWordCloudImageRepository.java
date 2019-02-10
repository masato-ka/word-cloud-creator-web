package ka.masato.blog.wordcloud.infra.imagerepo;

import com.google.appengine.tools.cloudstorage.*;
import com.google.auth.appengine.AppEngineCredentials;
import com.google.auth.oauth2.GoogleCredentials;

import com.google.cloud.storage.*;
import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import ka.masato.blog.wordcloud.domain.wordcloud.repository.WordCloudImageRepository;
import ka.masato.blog.wordcloud.infra.imagerepo.exceptions.FailedPersistImageException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Optional;

@Profile("appengine")
@Repository
public class GcsWordCloudImageRepository implements WordCloudImageRepository {


    private final String BUCKET_NAME;

    private Storage storage;
    private final String FILE_PROPERTY = "png";

    public GcsWordCloudImageRepository(@Value("${storage.gcs.backet:kataricloud}") String bucketName,
                                       @Value("${storage.gcs.buffer.size:1024}")int buffer_size) {
        this.BUCKET_NAME = bucketName;
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @Override
    public String saveImage(BufferedImage image) {
        BlobInfo blobInfo = BlobInfo
                .newBuilder(BUCKET_NAME, RandomStringUtils.randomAlphanumeric(20)+"."+ FILE_PROPERTY)
                .setAcl(new ArrayList<>(Arrays.asList(Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER))))
                .build();
        InputStream is = getInputStream(image).get();
        blobInfo = storage.create(blobInfo, is);
        return blobInfo.getMediaLink();
    }

    private Optional<InputStream> getInputStream(BufferedImage image) {
        Optional<InputStream> is = Optional.empty();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image,"png",baos);
            is = Optional.ofNullable(new ByteArrayInputStream(baos.toByteArray()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return is;
    }


    @Override
    public BufferedImage getImage(WordCloudMetaData imageMetadata) {
        return null;
    }

    @Override
    public void delete(String url) {

    }
}
