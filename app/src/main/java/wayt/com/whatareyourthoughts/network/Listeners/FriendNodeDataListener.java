package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import wayt.com.whatareyourthoughts.ShowFriendsActivity;
import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.Friends;

/**
 * Created by Pragya on 3/7/2017.
 */

public class FriendNodeDataListener implements ValueEventListener {

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

        Iterator friendDataIterator = dataSnapshot.getChildren().iterator();
        if(friendDataIterator.hasNext()){
            DataSnapshot obj = (DataSnapshot) friendDataIterator.next();
            String friendUserId = obj.getKey();
            String friendUserName = (String)obj.getValue();
            Friends friendData = new Friends();
            friendData.setFriendId(friendUserId);
            friendData.setFriendName(friendUserName);
            ShowFriendsActivity.addToAdapter(friendData);
        }
    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
