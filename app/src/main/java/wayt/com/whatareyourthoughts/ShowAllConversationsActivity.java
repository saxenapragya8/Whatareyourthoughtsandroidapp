package wayt.com.whatareyourthoughts;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wayt.com.whatareyourthoughts.adapters.ConversationFirebaseListAdapter;
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
        displayData.add(data);
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_conversations);
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
        navigationView.getMenu().getItem(0).setChecked(true);
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
