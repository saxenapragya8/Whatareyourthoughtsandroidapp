package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Comment;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.model.CommentData;
import wayt.com.whatareyourthoughts.model.DisplayData;

/**
 * Created by Pragya on 1/22/2017.
 */

public class ConversationCommentAdapter extends BaseAdapter{
    Context ctx;
    private List<CommentData> commentsData;
    private String subject;
    private String inspiration;
    private List<String> participantList;

    public ConversationCommentAdapter(DisplayData data, Context ctx){
        this.commentsData = data.getAllComments();
        this.subject = data.getSubject();
        this.inspiration = data.getInspiration();
        this.participantList = data.getParticipantUsers();
        this.ctx = ctx;
    }

    @Override
    public int getCount() {
        return commentsData.size();
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
        if(convertView == null)
        {
            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.conversation_comment_row, parent,false);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
        CommentData item = commentsData.get(position);

        TextView participantUser = (TextView)convertView.findViewById(R.id.participantUser);
        participantUser.setText(item.getUserName());

        TextView creationDate = (TextView)convertView.findViewById(R.id.creationDate);
        creationDate.setText(sdf.format(item.getModifiedDate()));

        TextView content = (TextView)convertView.findViewById(R.id.commentContent);
        content.setText(Html.fromHtml(item.getContent()));

        return convertView;
    }
}
