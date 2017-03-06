package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Response;

import org.json.JSONObject;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.HttpRequestSender;

/**
 * Created by Pragya on 2/12/2017.
 */

public class AddNewCommentListener implements Response.Listener<JSONObject> {

    Context ctx;
    public AddNewCommentListener(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onResponse(JSONObject response) {
        Toast.makeText(ctx, "Add new comment status" + response.toString(), Toast.LENGTH_LONG).show();
        HttpRequestSender.getInstance(ctx).getUserConversationsData(getUserId(), ctx);
    }

    private Integer getUserId(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(ctx.getString(R.string.user_id_field), 0);
    }
}
