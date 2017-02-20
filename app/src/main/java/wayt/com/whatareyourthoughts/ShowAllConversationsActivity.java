package wayt.com.whatareyourthoughts;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toolbar;

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

import wayt.com.whatareyourthoughts.adapters.DisplayDataAdapter;
import wayt.com.whatareyourthoughts.model.CommentData;
import wayt.com.whatareyourthoughts.model.DisplayData;

public class ShowAllConversationsActivity extends ListActivity{
    private DisplayDataAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_conversations);

//        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
//        myToolbar.setTitle(R.string.title_conversations);

        Intent intent = getIntent();

        String conversationsData = intent.getStringExtra("ALL_CONVERSATIONS_DATA");
        try {
            JSONObject jsonData = new JSONObject(conversationsData);
            List<DisplayData> displayData = getAllDisplayDataItems(jsonData);
            adapter = new DisplayDataAdapter(displayData, this);
            setListAdapter(adapter);
//            ListView displayConversations = (ListView)findViewById(R.id.displayConversationListView);
//            displayConversations.setAdapter(adapter);
            Log.e("ShowAllConversations", "Example Item: " + conversationsData);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private Map<Integer,List<CommentData>> getCommentDataMap(JSONArray commentsData) throws ParseException, JSONException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Map<Integer, List<CommentData>> commentData = new HashMap<Integer, List<CommentData>>();
        for (int j=0; j<commentsData.length(); j++) {
            Integer convId = commentsData.getJSONObject(j).getInt("convId");
            String content = commentsData.getJSONObject(j).getString("content");
            Date updatedAt = sdf.parse(commentsData.getJSONObject(j).getString("updatedAt"));
            CommentData commData = new CommentData();
            commData.setContent(content);
            commData.setModifiedDate(updatedAt);
            if(commentData.containsKey(convId)){
                commentData.get(convId).add(commData);
                Collections.sort(commentData.get(convId));
            } else {
                List<CommentData> commentDataList = new ArrayList<CommentData>();
                commentDataList.add(commData);
                commentData.put(convId, commentDataList);
            }
        }
        return commentData;
    }

    private List<DisplayData> getAllDisplayDataItems(JSONObject jsonData) {
        List<DisplayData> displayData = new ArrayList<DisplayData>();
        try {

            Map<Integer, List<CommentData>> commentData = getCommentDataMap(jsonData.getJSONArray("comments"));

            JSONArray conversationsData = jsonData.getJSONArray("conversations");
            JSONObject convIdUserNamesMap = jsonData.getJSONObject("convIdUserNamesMap");
            for(int i = 0; i < conversationsData.length(); i++){
                DisplayData data = new DisplayData();
                JSONArray convParticipantsList = convIdUserNamesMap.getJSONArray(conversationsData.getJSONObject(i).getString("id"));
                List<String> participants = new ArrayList<String>();
                for (int j=0; j<convParticipantsList.length(); j++) {
                    participants.add( convParticipantsList.getString(j) );
                }
                data.setConvId(conversationsData.getJSONObject(i).getInt("id"));
                data.setSubject(conversationsData.getJSONObject(i).getString("subject"));
                data.setInspiration(conversationsData.getJSONObject(i).getString("sourceLink"));
                data.setParticipantUsers(participants);
                CommentData latestComment = commentData.get(conversationsData.getJSONObject(i).getInt("id")).get(commentData.get(conversationsData.getJSONObject(i).getInt("id")).size() - 1);
                data.setLastConversation(latestComment);
                data.setAllComments(commentData.get(conversationsData.getJSONObject(i).getInt("id")));
                displayData.add(data);
            }

        }catch(JSONException e){
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return displayData;
    }

    public void onAddConvButtonClick(View addConvButton) {
        Intent addNewConvActivity = new Intent(addConvButton.getContext(), AddNewConversationActivity.class);
        startActivity(addNewConvActivity);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        DisplayData displayDataClicked = (DisplayData)adapter.getItem(position);
        Intent conversationCommentsActivity = new Intent(v.getContext(), ShowConversationCommentsActivity.class);
        conversationCommentsActivity.putExtra("commentData", displayDataClicked);
        startActivity(conversationCommentsActivity);
    }
}
