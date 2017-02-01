package wayt.com.whatareyourthoughts;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import wayt.com.whatareyourthoughts.network.HttpRequestSender;

public class AddNewConversationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_conversation);
    }

    public void onSaveDataClicked(View view){
        EditText content = (EditText) view.findViewById(R.id.content);
        EditText inspiration = (EditText)view.findViewById(R.id.inspiration);
        EditText subject = (EditText)view.findViewById(R.id.subject);
        EditText toEmails = (EditText)view.findViewById(R.id.toEmails);

        Editable comment = content.getText();
        String formatSavingText = Html.toHtml(comment);
        HttpRequestSender.getInstance(this).addNewConversation(getUserId(), this, subject.getText().toString(),
                inspiration.getText().toString(), toEmails.getText().toString(), formatSavingText);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(this.getString(R.string.user_id_field), 0);
    }
}
