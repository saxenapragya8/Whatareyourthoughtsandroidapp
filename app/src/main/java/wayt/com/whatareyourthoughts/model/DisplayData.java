package wayt.com.whatareyourthoughts.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * Created by Pragya on 1/16/2017.
 * space added by Pranav!
 */

public class DisplayData implements Serializable{


    private Integer convId;
    private String subject;
    private String inspiration;
    private List<String> participantUsers;
    private CommentData lastConversation;
    private List<CommentData> allComments;

    public Integer getConvId() {
        return convId;
    }

    public void setConvId(Integer convId) {
        this.convId = convId;
    }

    public List<CommentData> getAllComments() {
        return allComments;
    }

    public void setAllComments(List<CommentData> allComments) {
        this.allComments = allComments;
    }

    public String getSubject() {
        return subject;
    }

    public String getInspiration() {
        return inspiration;
    }

    public void setInspiration(String inspiration) {
        this.inspiration = inspiration;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public List<String> getParticipantUsers() {
        return participantUsers;
    }

    public void setParticipantUsers(List<String> participantUsers) {
        this.participantUsers = participantUsers;
    }

    public CommentData getLastConversation() {
        return lastConversation;
    }

    public void setLastConversation(CommentData lastConversation) {
        this.lastConversation = lastConversation;
    }
}
