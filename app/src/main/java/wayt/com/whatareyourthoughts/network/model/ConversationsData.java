package wayt.com.whatareyourthoughts.network.model;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;

/**
 * Created by Pragya on 3/1/2017.
 */

@IgnoreExtraProperties
public class ConversationsData implements Serializable{
    private String convId;
    private Long CreatedAt;
    private String CreatedByID;
    private String CreatedBy;
    private String Inspiration;
    private String Subject;
    private Map<String, CommentData> Comments = new HashMap<>();

    public String getConvId() {
        return convId;
    }

    public void setConvId(String convId) {
        this.convId = convId;
    }

    @Exclude
    public String getCreationDate(){
        Date date = new Date(CreatedAt);
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(date);
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getCreatedByID() {
        return CreatedByID;
    }

    public String getInspiration() {
        return Inspiration;
    }

    public String getSubject() {
        return Subject;
    }

    @Exclude
    public void addCommentNodeData(String commentId, CommentData commentData){
        Comments.put(commentId, commentData);
    }

    public void setCreatedAt(Long createdAt) {
        CreatedAt = createdAt;
    }

    public void setCreatedByID(String createdByID) {
        CreatedByID = createdByID;
    }

    public void setInspiration(String inspiration) {
        Inspiration = inspiration;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public Long getCreatedAt() {
        return CreatedAt;
    }

    public Map<String, CommentData> getComments() {
        return Comments;
    }

    public void setComments(Map<String, CommentData> comments) {
        Comments = comments;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put(RealtimeDbConstants.CREATED_AT, ServerValue.TIMESTAMP);
        result.put(RealtimeDbConstants.CREATED_BY_ID, CreatedByID);
        result.put("Inspiration", Inspiration);
        result.put("Subject", Subject);
        result.put("Comments", Comments);
        result.put(RealtimeDbConstants.CREATED_BY, CreatedBy);
        return result;
    }
}
