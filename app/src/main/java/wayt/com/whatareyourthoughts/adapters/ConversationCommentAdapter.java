package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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

public class ConversationCommentAdapter extends RecyclerView.Adapter<ConversationCommentAdapter.ViewHolder>{
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

    public static  class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View view) {
            super(view);
        }
    }

    public static class ViewHolderCommentDisplayRow extends ViewHolder {
        // each data item is just a string in this case
        public TextView participantUser;
        public TextView creationDate;
        public TextView content;

        public ViewHolderCommentDisplayRow(View view) {
            super(view);
            participantUser = (TextView) view.findViewById(R.id.participantUser);
            creationDate = (TextView) view.findViewById(R.id.creationDate);
            content = (TextView) view.findViewById(R.id.commentContent);
        }
    }

    public static class ViewHolderNewCommentRow extends ViewHolder {
        //No data to bind for adding new comment row
        public ViewHolderNewCommentRow(View view) {
            super(view);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if(viewType == 1) {
            View view = inflater.inflate(R.layout.details_conversation_item, parent, false);
            ConversationCommentAdapter.ViewHolder vh = new ConversationCommentAdapter.ViewHolderCommentDisplayRow(view);
            return vh;
        } else {
            View view = inflater.inflate(R.layout.details_conversation_new_comment, parent, false);
            ConversationCommentAdapter.ViewHolder vh = new ConversationCommentAdapter.ViewHolderNewCommentRow(view);
            return vh;
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if(holder instanceof  ViewHolderCommentDisplayRow){
            CommentData item = commentsData.get(position);
            ((ViewHolderCommentDisplayRow)holder).creationDate.setText(item.getCreationDate());
            ((ViewHolderCommentDisplayRow)holder).content.setText(Html.fromHtml(item.getCommentContent()));
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return commentsData.size();
    }

    @Override
    public int getItemViewType(int position){
        if(position < commentsData.size())
            return 1;
        else
            return 2;
    }
//
//    @Override
//    public int getCount() {
//        return commentsData.size() + 1;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return commentsData.get(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(position < commentsData.size() ) {
//
//            if (convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.details_conversation_item, parent, false);
//            }
//
//            CommentData item = commentsData.get(position);
//
//            TextView participantUser = (TextView) convertView.findViewById(R.id.participantUser);
////            participantUser.setText(item.getUserName());
//
//            TextView creationDate = (TextView) convertView.findViewById(R.id.creationDate);
//            creationDate.setText(item.getCreationDate());
//
//            TextView content = (TextView) convertView.findViewById(R.id.commentContent);
//            content.setText(Html.fromHtml(item.getCommentContent()));
//
//            return convertView;
//        } else {
//            if (convertView == null) {
//                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                convertView = inflater.inflate(R.layout.details_conversation_new_comment, parent, false);
//            }
//            return convertView;
//        }
//    }
}
