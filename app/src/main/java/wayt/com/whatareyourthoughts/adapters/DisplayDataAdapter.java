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
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.model.CommentData;
import wayt.com.whatareyourthoughts.model.DisplayData;
import wayt.com.whatareyourthoughts.responses.CommentResponse;

/**
 * Created by Pragya on 1/16/2017.
 */

public class DisplayDataAdapter extends BaseAdapter{

    List<DisplayData> displayData = new ArrayList<DisplayData>();
    Context ctx;

    public DisplayDataAdapter(List<DisplayData> displayData, Context ctx){
        this.ctx = ctx;
        this.displayData = displayData;
    }

    @Override
    public int getCount() {
        return displayData.size();
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

        DisplayData item = displayData.get(position);

        conversationName.setText(item.getSubject().trim());
        String time=sdf.format(item.getLastConversation().getModifiedDate());
        modifiedLastDate.setText(time);
        userNamesList.setText(item.getParticipantUsers().toString().trim());
        content.setText(Html.fromHtml(item.getLastConversation().getContent().trim()));
        return convertView;
    }
}
