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
        Log.d("UpdateRegIdListener", "Received all date response response" +  response.toString());
        Intent errorIntent = new Intent(ctx, ShowAllConversationsActivity.class);
        ctx.startActivity(errorIntent);
    }
}
