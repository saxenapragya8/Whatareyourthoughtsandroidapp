package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.renderscript.Sampler;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import wayt.com.whatareyourthoughts.ShowConversationCommentsActivity;
import wayt.com.whatareyourthoughts.network.model.CommentData;

/**
 * Created by Pragya on 3/1/2017.
 */

public class CommentNodeDataChangeListener implements ValueEventListener {
    Context ctx;

    public CommentNodeDataChangeListener(Context ctx){
        this.ctx = ctx;
    }

    //Comment id changed in a conversations node
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        String commentKey = dataSnapshot.getKey();
        String parent = dataSnapshot.getRef().getParent().getKey();
        CommentData commentData = dataSnapshot.getValue(CommentData.class);
        commentData.setCommentId(commentKey);
        ShowConversationCommentsActivity.addComment(commentData);

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
