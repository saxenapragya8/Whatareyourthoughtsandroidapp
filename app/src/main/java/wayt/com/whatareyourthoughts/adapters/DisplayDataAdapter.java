package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.adapters.listeners.OnConversationClickedListener;
import wayt.com.whatareyourthoughts.network.model.CommentData;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 1/16/2017.
 */

public class DisplayDataAdapter extends RecyclerView.Adapter<DisplayDataAdapter.ViewHolder>{


    List<ConversationsData> displayData;
    Context ctx;
    OnConversationClickedListener listener;

    public ConversationsData getData(int position){
        return displayData.get(position);
    }
    public DisplayDataAdapter(List<ConversationsData> displayData, Context ctx, OnConversationClickedListener listener){
        this.ctx = ctx;
        this.displayData = displayData;
        this.listener = listener;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView convNameTextView;
        public TextView modifiedLastDateTextView;
        public TextView content;
        public TextView userNamesList;

        public ViewHolder(View view) {
            super(view);
            convNameTextView = (TextView)view.findViewById(R.id.conversationName);
            modifiedLastDateTextView = (TextView)view.findViewById(R.id.modifiedLastDate);
            content = (TextView)view.findViewById(R.id.content);
            userNamesList = (TextView)view.findViewById(R.id.userNames);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.details_inbox_item, parent,false);
        view.setOnClickListener(listener);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ConversationsData item = displayData.get(position);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String time=sdf.format(item.getCreatedAt());

        holder.convNameTextView.setText(item.getSubject().trim());
        holder.modifiedLastDateTextView.setText(time);

        String uiDisplayUserString = "";
        if(item.getComments().size() != 0){
            Collection<CommentData> convCommData = item.getComments().values();
            List<CommentData> sortedCommentsByCreationTime = new ArrayList<CommentData>(convCommData);
            Collections.sort(sortedCommentsByCreationTime);
            CommentData lastConv = sortedCommentsByCreationTime.get(sortedCommentsByCreationTime.size() - 1);
            holder.content.setText(Html.fromHtml(lastConv.getCommentContent()));
            Set<String> stringCommentedUsers = new HashSet<>();
            for(CommentData comment : sortedCommentsByCreationTime){
                stringCommentedUsers.add(comment.getCommentCreatedByName());
            }
            uiDisplayUserString = stringCommentedUsers.toString();
            uiDisplayUserString = uiDisplayUserString.replace("[","");
            uiDisplayUserString = uiDisplayUserString.replace("]","");
            uiDisplayUserString = uiDisplayUserString + "(" +stringCommentedUsers.size() +  ")";
        }

        holder.userNamesList.setText(uiDisplayUserString);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return displayData.size();
    }
//
//
//    @Override
//    public int getCount() {
//        return displayData.size() ;
//    }
//
//    @Override
//    public Object getItem(int position) {
//        return displayData.get(position);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        if(convertView == null)
//        {
//            LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            convertView = inflater.inflate(R.layout.details_inbox_item, parent,false);
//        }
//
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);
//
//        parent.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
//        TextView conversationName = (TextView)convertView.findViewById(R.id.conversationName);
//        TextView modifiedLastDate = (TextView)convertView.findViewById(R.id.modifiedLastDate);
//        TextView userNamesList = (TextView)convertView.findViewById(R.id.userNames);
//        TextView content = (TextView)convertView.findViewById(R.id.content);
//
//        ConversationsData item = displayData.get(position);
//        conversationName.setText(item.getSubject().trim());
//        String time=sdf.format(item.getCreatedAt());
//        modifiedLastDate.setText(time);
//
//        String uiDisplayUserString = "";
//        if(item.getComments().size() != 0){
//            Collection<CommentData> convCommData = item.getComments().values();
//            List<CommentData> sortedCommentsByCreationTime = new ArrayList<CommentData>(convCommData);
//            Collections.sort(sortedCommentsByCreationTime);
//            CommentData lastConv = sortedCommentsByCreationTime.get(sortedCommentsByCreationTime.size() - 1);
//            content.setText(Html.fromHtml(lastConv.getCommentContent()));
//            Set<String> stringCommentedUsers = new HashSet<>();
//            for(CommentData comment : sortedCommentsByCreationTime){
//                stringCommentedUsers.add(comment.getCommentCreatedByName());
//            }
//            uiDisplayUserString = stringCommentedUsers.toString();
//            uiDisplayUserString = uiDisplayUserString.replace("[","");
//            uiDisplayUserString = uiDisplayUserString.replace("]","");
//            uiDisplayUserString = uiDisplayUserString + "(" +stringCommentedUsers.size() +  ")";
//        }
//
//        userNamesList.setText(uiDisplayUserString);
//        return convertView;
//    }
}
