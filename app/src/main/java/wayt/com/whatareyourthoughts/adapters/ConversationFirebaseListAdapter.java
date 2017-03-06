package wayt.com.whatareyourthoughts.adapters;

import android.app.Activity;
import android.provider.ContactsContract;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 3/5/2017.
 */

public class ConversationFirebaseListAdapter extends FirebaseListAdapter<ConversationsData> {

    public ConversationFirebaseListAdapter(DatabaseReference ref, Activity activity, int layout){
        super(activity, ConversationsData.class, layout, ref);
    }

    @Override
    protected void populateView(View v, ConversationsData model, int position) {
//        v.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        TextView conversationName = (TextView)v.findViewById(R.id.conversationName);
        TextView modifiedLastDate = (TextView)v.findViewById(R.id.modifiedLastDate);
        conversationName.setText(model.getSubject().trim());
        modifiedLastDate.setText(model.getCreationDate());
    }
}
