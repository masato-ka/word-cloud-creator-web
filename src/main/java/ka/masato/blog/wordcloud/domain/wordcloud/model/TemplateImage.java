package ka.masato.blog.wordcloud.domain.wordcloud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class TemplateImage {

    @Id
    @GeneratedValue
    Integer templateImageId;
    String templateName;
    String imagePath;

}
