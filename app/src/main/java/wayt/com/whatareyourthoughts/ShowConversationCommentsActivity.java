package wayt.com.whatareyourthoughts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import wayt.com.whatareyourthoughts.adapters.ConversationCommentAdapter;
import wayt.com.whatareyourthoughts.adapters.DisplayDataAdapter;
import wayt.com.whatareyourthoughts.model.DisplayData;
import wayt.com.whatareyourthoughts.network.HttpRequestSender;

public class ShowConversationCommentsActivity extends AppCompatActivity {

    DisplayData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_conversation_comments);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        data = (DisplayData) getIntent().getSerializableExtra("commentData");
        TextView participantList = (TextView)findViewById(R.id.participants);
        participantList.setText(data.getParticipantUsers().toString());

        TextView date = (TextView)findViewById(R.id.date);
        date.setText(data.getAllComments().get(0).getModifiedDate().toString());

        TextView subject = (TextView)findViewById(R.id.subject);
        subject.setText(data.getSubject());

        TextView inspiration = (TextView)findViewById(R.id.inspiration);
        inspiration.setText(data.getInspiration());

        ConversationCommentAdapter adapter = new ConversationCommentAdapter(data, this);
        ListView displayComments = (ListView)findViewById(R.id.displayCommentsListView);
        displayComments.setAdapter(adapter);
    }

    public void onCommentAddClicked(View addConvButton) {
        EditText comment = (EditText)findViewById(R.id.newComment);
        String newCommentText = comment.getText().toString();
        Integer convId = data.getConvId();
        Integer userId = getUserId();
        HttpRequestSender.getInstance(this).addNewComment(userId, convId, this, newCommentText);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(this.getString(R.string.user_id_field), 0);
    }
}
