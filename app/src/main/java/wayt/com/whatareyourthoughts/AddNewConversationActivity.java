package wayt.com.whatareyourthoughts;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;

public class AddNewConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_conversation);
    }

    public void onBackButtonClick(View view) {
        Intent allConversations = new Intent(this, ShowAllConversationsActivity.class);
        startActivity(allConversations);
    }

    public void onSendButtonClick(View view){
        EditText content = (EditText) this.findViewById(R.id.content);
        EditText inspiration = (EditText)this.findViewById(R.id.inspiration);
        EditText subject = (EditText)this.findViewById(R.id.subject);
        EditText toEmails = (EditText)this.findViewById(R.id.toEmails);

        Editable comment = content.getText();
        String formatSavingText = Html.toHtml(comment);

        RealtimeDbWriter.getInstance(this).writeNewConversationToDb(subject.getText().toString(),
                inspiration.getText().toString(), toEmails.getText().toString(), formatSavingText);

        Intent intent = new Intent(this, ShowAllConversationsActivity.class);
        this.startActivity(intent);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(this.getString(R.string.user_id_field), 0);
    }
}
