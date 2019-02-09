package ka.masato.blog.wordcloud.infra.rawTextRepository.exception;

public class MalformedTargetAddressException extends RuntimeException{

    public MalformedTargetAddressException(String message){
        super(message);
    }

}
