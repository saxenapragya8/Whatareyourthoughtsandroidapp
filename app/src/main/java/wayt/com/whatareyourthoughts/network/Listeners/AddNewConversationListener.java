package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by Pragya on 2/28/2017.
 */

public class AddNewConversationListener implements ValueEventListener {

    private DatabaseReference dbRef;
    private Context ctx;

    public AddNewConversationListener(DatabaseReference dbRef, Context ctx){
        this.dbRef = dbRef;
        this.ctx = ctx;
    }

    @Override
    public void onDataChange(DataSnapshot dataSnapshot) {

    }

    @Override
    public void onCancelled(DatabaseError databaseError) {

    }
}
