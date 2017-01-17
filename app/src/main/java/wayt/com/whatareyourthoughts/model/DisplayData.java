package wayt.com.whatareyourthoughts.model;

import java.util.Date;
import java.util.List;

/**
 * Created by Pragya on 1/16/2017.
 */

public class DisplayData {

    private String subject;
    private List<String> participantUsers;
    private String lastConversation;
    private Date lastConversationDate;

    public String getSubject() {
        return subject;
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

    public String getLastConversation() {
        return lastConversation;
    }

    public void setLastConversation(String lastConversation) {
        this.lastConversation = lastConversation;
    }

    public Date getLastConversationDate() {
        return lastConversationDate;
    }

    public void setLastConversationDate(Date lastConversationDate) {
        this.lastConversationDate = lastConversationDate;
    }
}
