package wayt.com.whatareyourthoughts.network.model;

import android.support.annotation.NonNull;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.database.ServerValue;

import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;

/**
 * Created by Pragya on 3/1/2017.
 */

@IgnoreExtraProperties
public class CommentData implements Serializable, Comparable<CommentData>{
    private String commentId;
    private String CommentContent;
    private String CommentCreatedByID;
    private String CommentCreatedByName;
    Map<String, Object> commentCreatedAt = new HashMap<String, Object>();

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public CommentData(){
        commentCreatedAt.put("CommentCreatedAt", ServerValue.TIMESTAMP);
    }

    public String getCommentContent() {
        return CommentContent;
    }

    public void setCommentContent(String commentContent) {
        CommentContent = commentContent;
    }

    @Exclude
    public String getCreationDate(){
        Date date = new Date((Long)commentCreatedAt.get("CommentCreatedAt"));
        Format format = new SimpleDateFormat("MM/dd/yyyy");
        return format.format(date);
    }

    public Map<String, Object> getCommentCreatedAt() {
        return commentCreatedAt;
    }

    public void setCommentCreatedAt(Map<String, Object> commentCreatedAt) {
        this.commentCreatedAt = commentCreatedAt;
    }

    public String getCommentCreatedByID() {
        return CommentCreatedByID;
    }

    public void setCommentCreatedByID(String commentCreatedByID) {
        CommentCreatedByID = commentCreatedByID;
    }

    public String getCommentCreatedByName() {
        return CommentCreatedByName;
    }

    public void setCommentCreatedByName(String commentCreatedByName) {
        CommentCreatedByName = commentCreatedByName;
    }

    @Exclude
    public Map<String, Object> toMap(){
        Map<String, Object> result = new HashMap<>();
        result.put("CommentCreatedAt", ServerValue.TIMESTAMP);
        result.put("CommentContent", CommentContent);
        result.put("CommentCreatedByID", CommentCreatedByID);
        result.put("CommentCreatedByName", CommentCreatedByName);
        return result;
    }

    @Override
    public int compareTo(@NonNull CommentData o) {
        return 0;
    }
}
