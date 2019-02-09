package ka.masato.blog.wordcloud.infra.rawtext.exception;

public class MalformedTargetAddressException extends RuntimeException{

    public MalformedTargetAddressException(String message){
        super(message);
    }

}
