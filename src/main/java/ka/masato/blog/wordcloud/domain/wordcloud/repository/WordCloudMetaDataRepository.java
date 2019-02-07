package ka.masato.blog.wordcloud.domain.wordcloud.repository;

import ka.masato.blog.wordcloud.domain.wordcloud.model.WordCloudMetaData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WordCloudMetaDataRepository extends JpaRepository<WordCloudMetaData, Integer> {
}
