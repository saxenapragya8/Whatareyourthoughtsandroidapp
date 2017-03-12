package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.model.CommentData;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;

/**
 * Created by Pragya on 1/16/2017.
 */

public class DisplayDataAdapter extends BaseAdapter{

    List<ConversationsData> displayData;
    Context ctx;

    public DisplayDataAdapter(List<ConversationsData> displayData, Context ctx){
        this.ctx = ctx;
        this.displayData = displayData;
    }

    @Override
    public int getCount() {
        return displayData.size() ;
    }

    @Override
    public Object getItem(int position) {
        return displayData.get(position);
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
            convertView = inflater.inflate(R.layout.display_conversations_row, parent,false);
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.US);

        parent.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
        TextView conversationName = (TextView)convertView.findViewById(R.id.conversationName);
        TextView modifiedLastDate = (TextView)convertView.findViewById(R.id.modifiedLastDate);
        TextView userNamesList = (TextView)convertView.findViewById(R.id.userNames);
        TextView content = (TextView)convertView.findViewById(R.id.content);

        ConversationsData item = displayData.get(position);
        conversationName.setText(item.getSubject().trim());
        String time=sdf.format(item.getCreatedAt());
        modifiedLastDate.setText(time);

        String uiDisplayUserString = "";
        if(item.getComments().size() != 0){
            Collection<CommentData> convCommData = item.getComments().values();
            List<CommentData> sortedCommentsByCreationTime = new ArrayList<CommentData>(convCommData);
            Collections.sort(sortedCommentsByCreationTime);
            CommentData lastConv = sortedCommentsByCreationTime.get(sortedCommentsByCreationTime.size() - 1);
            content.setText(Html.fromHtml(lastConv.getCommentContent()));
            Set<String> stringCommentedUsers = new HashSet<>();
            for(CommentData comment : sortedCommentsByCreationTime){
                stringCommentedUsers.add(comment.getCommentCreatedByName());
            }
            uiDisplayUserString = stringCommentedUsers.toString();
            uiDisplayUserString = uiDisplayUserString.replace("[","");
            uiDisplayUserString = uiDisplayUserString.replace("]","");
            uiDisplayUserString = uiDisplayUserString + "(" +stringCommentedUsers.size() +  ")";
        }

        userNamesList.setText(uiDisplayUserString);
        return convertView;
    }
}
