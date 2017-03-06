package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.util.Log;

import com.android.volley.Response;

import org.json.JSONObject;

/**
 * Created by Pragya on 1/8/2017.
 */

public class UpdateRegistrationIdListener implements Response.Listener<JSONObject> {
    private Context ctx;

    public UpdateRegistrationIdListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onResponse(JSONObject response) {
        Log.d("UpdateRegIdListener", "Received addRegistrationId response" +  response.toString());
    }
}
