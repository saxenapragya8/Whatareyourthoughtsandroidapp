package wayt.com.whatareyourthoughts.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.android.gms.games.multiplayer.realtime.RealTimeMessage;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.security.Timestamp;
import java.util.HashMap;
import java.util.Map;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.Listeners.FriendNodeDataListener;
import wayt.com.whatareyourthoughts.network.model.CommentData;
import wayt.com.whatareyourthoughts.network.Listeners.UserConversationsDataChangeListener;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;
import wayt.com.whatareyourthoughts.network.model.Friends;
import wayt.com.whatareyourthoughts.network.model.UserData;

/**
 * Created by Pragya on 2/27/2017.
 */

public class RealtimeDbWriter {

    private  Context ctx;
    private static RealtimeDbWriter instance;
    DatabaseReference database = FirebaseDatabase.getInstance().getReference();

    private RealtimeDbWriter(Context context){ this.ctx = context;}

    public static synchronized RealtimeDbWriter getInstance(Context context)
    {
        if (null == instance)
            instance = new RealtimeDbWriter(context);
        return instance;
    }

    public DatabaseReference getDbReference(){
        return database;
    }

    public void addDataChangeListeners(){
        DatabaseReference userConvNodeRef = database.child(RealtimeDbConstants.USER_NODE).child(getUserId()).child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS);
        userConvNodeRef.orderByKey().addChildEventListener(new UserConversationsDataChangeListener(ctx));

        DatabaseReference userFriendList = database.child(RealtimeDbConstants.USER_NODE).child(getUserId()).child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.FRIENDS);
        userFriendList.addValueEventListener(new FriendNodeDataListener(ctx));
    }

    public void writeUserDataToFirebase(UserData data){
        // Write a message to the database
        storeUserIdInPrefs(data.getuId(), data.getUserName());
        DatabaseReference userAddRef = database.child(RealtimeDbConstants.USER_NODE).child(data.getuId());
        if(userAddRef != null) {
            userAddRef.child(RealtimeDbConstants.EMAIL).setValue(data.getEmail());
            userAddRef.child(RealtimeDbConstants.USER_NAME).setValue(data.getUserName());
        }
    }

    private void storeUserIdInPrefs(String userId, String userDisplayName){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.user_id_field), userId);
        editor.putString(ctx.getString(R.string.user_display_name_field), userDisplayName);
        editor.commit();
    }

    private String getUserId(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPref.getString(ctx.getString(R.string.user_id_field), "");
    }

    private String getUserDisplayName(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.app_name), Context.MODE_PRIVATE);
        return sharedPref.getString(ctx.getString(R.string.user_display_name_field), "");
    }

    public void writeNewConversationToDb(String subject, String inspiration, String emails, String content) {
        DatabaseReference conversationsNode = database.child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS);

        String conversationKey = conversationsNode.push().getKey();
        DatabaseReference commentNode = database.child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS).child(conversationKey).child(RealtimeDbConstants.COMMENTS);
        String commentKey = commentNode.push().getKey();

        CommentData commentData = new CommentData();
        commentData.setCommentContent(content);
        commentData.setCommentCreatedByID(getUserId());
        commentData.setCommentCreatedByName(getUserDisplayName());

        ConversationsData conversationData = new ConversationsData();
        conversationData.setSubject(subject);
        conversationData.setInspiration(inspiration);
        conversationData.setCreatedByID(getUserId());
        conversationData.setCreatedBy(getUserDisplayName());
        conversationData.addCommentNodeData(commentKey, commentData);
        Map<String, Object> conversationMap = conversationData.toMap();

        conversationsNode.child(conversationKey).updateChildren(conversationMap);

        DatabaseReference userIdNode = database.child(RealtimeDbConstants.USER_NODE).child(getUserId()).child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS);
        Map<String, Object> userConversationData = new HashMap<>();
        userConversationData.put(conversationKey, "");
        userIdNode.updateChildren(userConversationData);
        Toast.makeText(ctx, " Added the new conversation " , Toast.LENGTH_LONG).show();
    }

    public void addNewComment(String convId, String content) {
        CommentData commentData = new CommentData();
        commentData.setCommentContent(content);
        commentData.setCommentCreatedByID(getUserId());
        commentData.setCommentCreatedByName(getUserDisplayName());
        DatabaseReference commentNode = database.child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS).child(convId).child(RealtimeDbConstants.COMMENTS);
        commentNode.push().setValue(commentData);
    }

    public void addFriend(String friendUserId, String friendUserName){
        DatabaseReference userFriendList = database.child(RealtimeDbConstants.USER_NODE).child(getUserId()).child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.FRIENDS);
//        String key = userFriendList.push().getKey();
        userFriendList.child(friendUserId).child(RealtimeDbConstants.FRIEND_STATUS).setValue(RealtimeDbConstants.INVITED);
        userFriendList.child(friendUserId).child(RealtimeDbConstants.FRIEND_NAME).setValue(friendUserName);
    }

    public void addUserToFriendNode(String friendUserId){
        DatabaseReference friendsFriendList = database.child(RealtimeDbConstants.USER_NODE).child(friendUserId).child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.FRIENDS);
        friendsFriendList.child(getUserId()).child(RealtimeDbConstants.FRIEND_STATUS).setValue(RealtimeDbConstants.ACCEPT_INVITE);
        friendsFriendList.child(getUserId()).child(RealtimeDbConstants.FRIEND_NAME).setValue(getUserDisplayName());
    }
}
