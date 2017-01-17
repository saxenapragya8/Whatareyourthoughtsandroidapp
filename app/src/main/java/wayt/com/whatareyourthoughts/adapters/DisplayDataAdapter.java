package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.model.DisplayData;

/**
 * Created by Pragya on 1/16/2017.
 */

public class DisplayDataAdapter extends BaseAdapter{

    List<DisplayData> displayData;
    Context ctx;

    public DisplayDataAdapter(JSONObject jsonData, Context ctx){
        this.ctx = ctx;
        getAllDisplayDataItems(jsonData);
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

        TextView conversationName = (TextView)convertView.findViewById(R.id.conversationName);
//        TextView itemDescription = (TextView)convertView.findViewById(R.id.itemDescription);
//        TextView status = (TextView)convertView.findViewById(R.id.status);

        DisplayData item = displayData.get(position);

        conversationName.setText(item.getSubject());
//        itemDescription.setText(item.getItemDescription());
//        status.setText(item.getItemStatus());

        return convertView;
    }

    private void getAllDisplayDataItems(JSONObject jsonData) {
        this.displayData = new ArrayList<>();
        try {
            JSONArray conversationsData = jsonData.getJSONArray("conversations");
            for(int i = 0; i < conversationsData.length(); i++){
                DisplayData data = new DisplayData();
                data.setSubject(conversationsData.getJSONObject(i).getString("subject"));
                this.displayData.add(data);
            }

            JSONArray participationsData = jsonData.getJSONArray("participations");
            JSONArray commentsData = jsonData.getJSONArray("comments");
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
