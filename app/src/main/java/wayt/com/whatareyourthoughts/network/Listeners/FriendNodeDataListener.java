package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import wayt.com.whatareyourthoughts.LoginActivity;
import wayt.com.whatareyourthoughts.SendEmailInviteActivity;
import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;
import wayt.com.whatareyourthoughts.ShowFriendsActivity;
import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.Friends;

/**
 * Created by Pragya on 3/7/2017.
 */

public class FriendNodeDataListener implements ValueEventListener {

    private Context ctx;
    public FriendNodeDataListener(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterator friendDataIterator = dataSnapshot.getChildren().iterator();
        if(friendDataIterator.hasNext()){
            DataSnapshot obj = (DataSnapshot) friendDataIterator.next();
            String friendUserId = obj.getKey();
            String friendStatus = (String)obj.child(RealtimeDbConstants.FRIEND_STATUS).getValue();
            String friendUserName = (String)obj.child(RealtimeDbConstants.FRIEND_NAME).getValue();
//            String friendUserName = (String)obj.getValue();
            Friends friendData = new Friends();
            friendData.setFriendId(friendUserId);
            friendData.setFriendName(friendUserName);
            friendData.setStatus(friendStatus);
            ShowFriendsActivity.addToAdapter(friendData);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
