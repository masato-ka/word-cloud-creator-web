package ka.masato.blog.wordcloud.infra.imagerepo.exceptions;

public class FailedPersistImageException extends RuntimeException {
    public FailedPersistImageException(String s) {
        super(s);
    }
}
