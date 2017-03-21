package wayt.com.whatareyourthoughts.adapters.listeners;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import wayt.com.whatareyourthoughts.ShowConversationCommentsActivity;
import wayt.com.whatareyourthoughts.adapters.DisplayDataAdapter;
import wayt.com.whatareyourthoughts.network.model.CommentData;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 3/21/2017.
 */

public class OnConversationClickedListener implements View.OnClickListener {

    RecyclerView recView;
    Context ctx;

    public OnConversationClickedListener(RecyclerView recyclerView, Context ctx){
        this.recView = recyclerView;
        this.ctx = ctx;
    }

    @Override
    public void onClick(View v) {
        int itemPosition = recView.getChildLayoutPosition(v);

        ConversationsData displayDataClicked = (ConversationsData)((DisplayDataAdapter)recView.getAdapter()).getData(itemPosition);

        Intent conversationCommentsActivity = new Intent(v.getContext(), ShowConversationCommentsActivity.class);
        conversationCommentsActivity.putExtra("commentData", displayDataClicked);
        ctx.startActivity(conversationCommentsActivity);
    }
}
