package ka.masato.blog.wordcloud.domain.wordcloud.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class ColorStyle {

    @Id
    @GeneratedValue
    Integer colorStyleId;
    String colorStyleName;

}
