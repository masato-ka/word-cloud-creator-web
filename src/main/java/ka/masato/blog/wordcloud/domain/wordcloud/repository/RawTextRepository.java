package ka.masato.blog.wordcloud.domain.wordcloud.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface RawTextRepository {

    public String getRawText(String url);

}
