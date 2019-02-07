package ka.masato.blog.wordcloud.domain.wordcloud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class WordCloudSetting {

    @Id
    @GeneratedValue
    private Integer settingId;
    private String shapeType;
    private int width;
    private int height;

    public WordCloudSetting(String type, int width, int height) {
        this.shapeType = type;
        this.width = width;
        this.height = height;
    }
}
