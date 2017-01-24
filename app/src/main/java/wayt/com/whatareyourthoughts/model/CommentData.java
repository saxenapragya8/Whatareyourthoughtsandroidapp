package wayt.com.whatareyourthoughts.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pragya on 1/16/2017.
 */

public class CommentData implements Comparable<CommentData>, Serializable{
    private String userName;
    private String content;
    private Date modifiedDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Override
    public int compareTo(CommentData o) {
        return this.modifiedDate.compareTo(o.modifiedDate);
    }
}
