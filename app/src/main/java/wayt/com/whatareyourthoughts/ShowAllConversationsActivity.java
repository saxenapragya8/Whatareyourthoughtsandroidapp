package wayt.com.whatareyourthoughts;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import wayt.com.whatareyourthoughts.adapters.DisplayDataAdapter;
import wayt.com.whatareyourthoughts.model.DisplayData;

public class ShowAllConversationsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_conversations);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        Intent intent = getIntent();

        String conversationsData = intent.getStringExtra("ALL_CONVERSATIONS_DATA");
        try {
            JSONObject jsonData = new JSONObject(conversationsData);
            DisplayDataAdapter adapter = new DisplayDataAdapter(jsonData, this);
            ListView wishListView = (ListView)findViewById(R.id.displayConversationListView);
            wishListView.setAdapter(adapter);

            Log.e("ShowAllConversations", "Example Item: " + conversationsData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
}
