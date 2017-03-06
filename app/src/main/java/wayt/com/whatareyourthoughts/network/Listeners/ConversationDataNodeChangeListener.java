package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;
import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbReader;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 3/1/2017.
 */

public class ConversationDataNodeChangeListener implements ValueEventListener {

    private Context ctx;

    public ConversationDataNodeChangeListener(Context ctx){
        this.ctx = ctx;
    }

    //fires when a WAYT/Conversations/$convId node changes data. Should be called single time on each conv id change in user nde
    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
//        dataSnapshot
        ConversationsData userConversation = dataSnapshot.getValue(ConversationsData.class);
        String convId = dataSnapshot.getKey();
        userConversation.setConvId(convId);
        ShowAllConversationsActivity.addToAdapter(userConversation);
        DatabaseReference ref = RealtimeDbWriter.getInstance(ctx).getDbReference().child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS).child(convId).child(RealtimeDbConstants.COMMENTS);
        ref.addValueEventListener(new CommentNodeDataChangeListener(ctx));
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
