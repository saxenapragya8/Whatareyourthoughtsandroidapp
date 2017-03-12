package wayt.com.whatareyourthoughts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import wayt.com.whatareyourthoughts.adapters.DisplayFriendListAdapter;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;
import wayt.com.whatareyourthoughts.network.model.Friends;

public class ShowFriendsActivity extends AppCompatActivity {

    static DisplayFriendListAdapter adapter;
    static List<Friends> friendData= new ArrayList<Friends>();;

    public static void addToAdapter(Friends data){
        if(data != null) {
            if(friendData == null){
                friendData = new ArrayList<Friends>();
            }
            friendData.add(data);
            if (adapter != null)
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_friends);
        ListView view = (ListView) findViewById(R.id.friendListView);
        adapter = new DisplayFriendListAdapter(this, friendData);
        view.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void onAddFriendButtonClick(View view){
        Intent intent = new Intent(this, AddFriendActivity.class);
        this.startActivity(intent);
    }
}
