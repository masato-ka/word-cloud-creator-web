package ka.masato.blog.wordcloud.controller.model;

public class WordCloudModel {

    private String word;
    private Long count;

    public WordCloudModel(){}

    public WordCloudModel(String word, Long count) {
        this.word = word;
        this.count = count;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }


}
