package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;

/**
 * Created by Pragya on 1/8/2017.
 */

public class GetAllDisplayDataListener implements Response.Listener<JSONObject> {
    private Context ctx;

    public GetAllDisplayDataListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("displayDataResponse", "Received all conversation data response response" +  response.toString());
        Intent allConvsIntent = new Intent(ctx, ShowAllConversationsActivity.class);
        allConvsIntent.putExtra("ALL_CONVERSATIONS_DATA", response.toString());
        ctx.startActivity(allConvsIntent);
    }
}
