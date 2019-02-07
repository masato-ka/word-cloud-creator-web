package ka.masato.blog.wordcloud.infra;

import ka.masato.blog.wordcloud.domain.wordcloud.repository.RawTextRepository;
import org.springframework.stereotype.Repository;

@Repository
public class MockRawTextRepository implements RawTextRepository {
    @Override
    public String getRawText(String url) {
        return "お寿司が食べたい。";
    }
}
