package wayt.com.whatareyourthoughts.network;

import android.content.Context;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import wayt.com.whatareyourthoughts.network.Listeners.CommentNodeDataChangeListener;
import wayt.com.whatareyourthoughts.network.Listeners.ConversationDataNodeChangeListener;
import wayt.com.whatareyourthoughts.network.Listeners.NewFriendAddEmailFoundListener;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 3/1/2017.
 */

public class RealtimeDbReader {

    private Context ctx;
    private static RealtimeDbReader instance;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private RealtimeDbReader(Context context){ this.ctx = context;}

    public static synchronized RealtimeDbReader getInstance(Context context)
    {
        if (null == instance)
            instance = new RealtimeDbReader(context);
        return instance;
    }

    public void getEmailUserId(String email){
        database.child(RealtimeDbConstants.USER_NODE).orderByChild(RealtimeDbConstants.EMAIL).equalTo(email)
                .addListenerForSingleValueEvent(new NewFriendAddEmailFoundListener(ctx));
    }
}
