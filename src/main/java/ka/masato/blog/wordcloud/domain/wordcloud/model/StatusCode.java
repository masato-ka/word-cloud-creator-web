package ka.masato.blog.wordcloud.domain.wordcloud.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class StatusCode {

    @Id
    private String statusCode;


    public StatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    @Override
    public String toString() {
        return "StatusCode{" +
                "statusCode='" + statusCode + '\'' +
                '}';
    }
}
