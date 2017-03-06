package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbReader;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;

/**
 * Created by Pragya on 2/27/2017.
 */

public class UserConversationsDataChangeListener implements ChildEventListener{

    private Context ctx;

    public UserConversationsDataChangeListener(Context ctx){
        this.ctx = ctx;
    }

    //returns map of conversation keys
//    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
//        HashMap<String, Object> userConversations = (HashMap<String, Object>)dataSnapshot.getValue();
//        Set<String> conversationIds = userConversations.keySet();
//        RealtimeDbReader.getInstance(ctx).subscribeToUserDataNodes(conversationIds);
//        Toast.makeText(ctx, "User conversation data changed " + userConversations, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
        DatabaseReference ref = RealtimeDbWriter.getInstance(ctx).getDbReference().child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS);
        ref.child(dataSnapshot.getKey()).addListenerForSingleValueEvent(new ConversationDataNodeChangeListener(ctx));
//        HashMap<String, Object> userConversationIds = (HashMap<String, Object>)dataSnapshot.getValue();
//        Set<String> conversationIds = userConversationIds.keySet();
    }

    @Override
    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onChildRemoved(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {
        Toast.makeText(ctx, "Cancelled change user data " + databaseError, Toast.LENGTH_LONG).show();
    }
}
