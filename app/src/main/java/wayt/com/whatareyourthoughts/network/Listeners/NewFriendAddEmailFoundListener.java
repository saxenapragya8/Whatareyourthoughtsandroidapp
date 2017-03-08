package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.content.Intent;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Iterator;

import wayt.com.whatareyourthoughts.ShowFriendsActivity;
import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;

/**
 * Created by Pragya on 3/7/2017.
 */

public class NewFriendAddEmailFoundListener implements ValueEventListener{
    Context ctx;

    public NewFriendAddEmailFoundListener(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {
        DatabaseReference friendFriendsNode = dataSnapshot.getRef().getParent().child(RealtimeDbConstants.FRIENDS);

        Iterator friendData = dataSnapshot.getChildren().iterator();
        if(friendData.hasNext()){
            DataSnapshot obj = (DataSnapshot) friendData.next();
            String friendUserId = obj.getKey();
            String friendUserName = (String)obj.child(RealtimeDbConstants.USER_NAME).getValue();

            RealtimeDbWriter.getInstance(ctx).addUserToFriendNode(friendUserId);
            RealtimeDbWriter.getInstance(ctx).addFriend(friendUserId, friendUserName);

            Intent intent = new Intent(ctx, ShowFriendsActivity.class);
            ctx.startActivity(intent);
        } else {
            //dint find friend in db
        }

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
