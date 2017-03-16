package wayt.com.whatareyourthoughts;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;

import wayt.com.whatareyourthoughts.adapters.DisplayDataAdapter;
import wayt.com.whatareyourthoughts.network.RealtimeDbConstants;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

public class ShowAllConversationsActivity extends ListActivity{
    private static DisplayDataAdapter adapter;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private static List<ConversationsData> displayData = new ArrayList<ConversationsData>();

    public static void addToAdapter(ConversationsData data){
        if(data != null) {
            displayData.add(data);
            if(adapter != null)
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_inbox);
        Intent intent = getIntent();
            DatabaseReference ref = RealtimeDbWriter.getInstance(this).getDbReference().child(RealtimeDbConstants.APP_ID).child(RealtimeDbConstants.CONVERSATIONS);
            adapter = new DisplayDataAdapter(displayData, this);
            setListAdapter(adapter);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
    }

    public void onDrawerButtonClick(View view){
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.openDrawer(Gravity.LEFT);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.getMenu().getItem(2).setChecked(true); //what's happening?
        navigationView.setNavigationItemSelectedListener(new  NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);

                switch (menuItem.getItemId()) {
                    case R.id.navFriends: //do what you want to do;
                        Intent showFriendActivity = new Intent(ShowAllConversationsActivity.this, ShowFriendsActivity.class);
                        startActivity(showFriendActivity);
                        break;
                    case R.id.navSettings: // etc,
                }
                return true;
            }
        });
    }

    public void onAddConvButtonClick(View addConvButton) {
        Intent addNewConvActivity = new Intent(addConvButton.getContext(), AddNewConversationActivity.class);
        startActivity(addNewConvActivity);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ConversationsData displayDataClicked = (ConversationsData)adapter.getItem(position);
        Intent conversationCommentsActivity = new Intent(v.getContext(), ShowConversationCommentsActivity.class);
        conversationCommentsActivity.putExtra("commentData", displayDataClicked);
        startActivity(conversationCommentsActivity);
    }
}
