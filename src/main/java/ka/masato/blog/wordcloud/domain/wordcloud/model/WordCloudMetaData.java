package ka.masato.blog.wordcloud.domain.wordcloud.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
public class WordCloudMetaData {

    @Id
    @GeneratedValue
    private Integer wordCloudId;
    private String url;
    private String hash;
    private String imagePath;
    private LocalDateTime atLastUpdate;
    private LocalDateTime atCreated;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name="setting_id")
    private WordCloudSetting wordCloudSetting;

    @ManyToOne
    @JoinColumn(name="status_code")
    private StatusCode statusCode;

    public WordCloudMetaData(String initialStatus){
        statusCode = new StatusCode(initialStatus);
    }


    public Integer getWordCloudId() {
        return wordCloudId;
    }

    public void setWordCloudId(Integer wordCloudId) {
        this.wordCloudId = wordCloudId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }


    public LocalDateTime getAtLastUpdate() {
        return atLastUpdate;
    }

    public void setAtLastUpdate(LocalDateTime atLastUpdate) {
        this.atLastUpdate = atLastUpdate;
    }

    public LocalDateTime getAtCreated() {
        return atCreated;
    }

    public void setAtCreated(LocalDateTime atCreated) {
        this.atCreated = atCreated;
    }

    public WordCloudSetting getWordCloudSetting() {
        return wordCloudSetting;
    }

    public void setWordCloudSetting(WordCloudSetting wordCloudSetting) {
        this.wordCloudSetting = wordCloudSetting;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public WordCloudSetting updateWordCloudSetting(String type, int width, int height) {
        this.wordCloudSetting = new WordCloudSetting(type, width, height);
        return wordCloudSetting;
    }
}
