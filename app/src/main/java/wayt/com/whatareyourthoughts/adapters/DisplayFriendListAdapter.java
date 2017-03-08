package wayt.com.whatareyourthoughts.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.model.ConversationsData;
import wayt.com.whatareyourthoughts.network.model.Friends;

/**
 * Created by Pragya on 3/7/2017.
 */

public class DisplayFriendListAdapter  extends BaseAdapter {

    List<Friends> friendsList;
    Context ctx;

    public DisplayFriendListAdapter(Context ctx, List<Friends> friendList){
        this.ctx = ctx;
        this.friendsList = friendList;
    }

    @Override
    public int getCount() {
        return friendsList.size();
    }

    @Override
    public Object getItem(int position) {
        return friendsList.get(position);
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
            convertView = inflater.inflate(R.layout.friend_list_row, parent,false);
        }
        TextView friendName = (TextView)convertView.findViewById(R.id.friendName);
        Friends item = friendsList.get(position);
        friendName.setText(item.getFriendName());
        return convertView;
    }
}
