package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.model.CommentData;

/**
 * Created by Pragya on 1/22/2017.
 */

public class ConversationCommentAdapter extends BaseAdapter{
    Context ctx;
    private List<CommentData> commentsData = new ArrayList<CommentData>();
//    private String subject;
//    private String inspiration;
//    private List<String> participantList;

    public ConversationCommentAdapter(List<CommentData> comments, Context ctx){
        commentsData = comments;
//        this.subject = subject;
//        this.inspiration = inspiration;
//        this.participantList = data.getParticipantUsers();
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return commentsData.size() + 1;
    }

    @Override
    public Object getItem(int position) {
        return commentsData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(position < commentsData.size() ) {

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.details_conversation_item, parent, false);
            }

            CommentData item = commentsData.get(position);

            TextView participantUser = (TextView) convertView.findViewById(R.id.participantUser);
//            participantUser.setText(item.getUserName());

            TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);
            creationDate.setText(item.getCreationDate());

            TextView content = (TextView) convertView.findViewById(R.id.commentContent);
            content.setText(Html.fromHtml(item.getCommentContent()));

            return convertView;
        } else {
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.details_conversation_new_comment, parent, false);
            }
            return convertView;
        }
    }
}
