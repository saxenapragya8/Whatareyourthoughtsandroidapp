package wayt.com.whatareyourthoughts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import wayt.com.whatareyourthoughts.network.RealtimeDbReader;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;

public class AddFriendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);
    }

    public void onSendInvite(View view){
        TextView emailInput = (TextView) findViewById(R.id.friendEmail);
        RealtimeDbReader.getInstance(this).getEmailUserId(emailInput.getText().toString());
    }
}
