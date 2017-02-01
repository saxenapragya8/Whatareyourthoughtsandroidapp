package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONObject;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.HttpRequestSender;

/**
 * Created by Pragya on 1/31/2017.
 */

public class AddNewConversationListener implements Response.Listener<JSONObject> {
    Context ctx;
    public AddNewConversationListener(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(ctx, "Add new conversation response" + response.toString(), Toast.LENGTH_LONG).show();
        Integer userId = getUserId();
        getAllUserDisplayData(userId, ctx);
    }

    private void getAllUserDisplayData(Integer userId, Context ctx) {
        HttpRequestSender.getInstance(ctx).getUserConversationsData(userId, ctx);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(ctx.getString(R.string.user_id_field), 0);
    }
}
