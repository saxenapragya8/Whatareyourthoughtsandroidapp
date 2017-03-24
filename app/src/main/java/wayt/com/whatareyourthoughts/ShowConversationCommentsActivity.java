package wayt.com.whatareyourthoughts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import wayt.com.whatareyourthoughts.adapters.ConversationCommentAdapter;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.CommentData;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

public class ShowConversationCommentsActivity extends AppCompatActivity {

    static ConversationsData data;
    static ConversationCommentAdapter adapter;

    public static void addComment(CommentData commentData){
        if(commentData != null && data != null) {
            Map<String, CommentData> commentsData = data.getComments();
            commentsData.put(commentData.getCommentId(), commentData);
            if(adapter != null)
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_conversation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        data = (ConversationsData) getIntent().getSerializableExtra("commentData");
//        TextView participantList = (TextView)findViewById(R.id.participants);
//        participantList.setText(data.getParticipantUsers().toString());
//
//        TextView date = (TextView)findViewById(R.id.date);
//        date.setText(data.getAllComments().get(0).getModifiedDate().toString());

        TextView subject = (TextView)findViewById(R.id.conversation_subject);
        subject.setText(data.getSubject());

        TextView inspiration = (TextView)findViewById(R.id.inspiration);
        inspiration.setText(data.getInspiration());

        Collection<CommentData> convCommData = data.getComments().values();
        List<CommentData> sortedCommentsByCreationTime = new ArrayList<CommentData>(convCommData);
        Collections.sort(sortedCommentsByCreationTime);

        adapter = new ConversationCommentAdapter(sortedCommentsByCreationTime, this);
        RecyclerView displayComments = (RecyclerView)findViewById(R.id.displayCommentsRecyclerView);

        // use a linear layout manager
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        displayComments.setLayoutManager(mLayoutManager);

        displayComments.setAdapter(adapter);
    }

    public void onBackButtonClick(View view) {
        Intent allConversations = new Intent(this, ShowAllConversationsActivity.class);
        startActivity(allConversations);
    }

    public void onSendButtonClick(View addConvButton) {
        EditText comment = (EditText)findViewById(R.id.newComment);
        String newCommentText = comment.getText().toString();
        String convId = data.getConvId();
        RealtimeDbWriter.getInstance(this).addNewComment(convId, newCommentText);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(this.getString(R.string.user_id_field), 0);
    }
}
