package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import wayt.com.whatareyourthoughts.LoginActivity;
import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;

/**
 * Created by Pragya on 1/31/2017.
 */

public class GetAllDisplayDataErrorListener implements Response.ErrorListener {

    private Context ctx;

    public GetAllDisplayDataErrorListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("displayDataRespError", "Error getting all conversations display data" +  error.getMessage());
        Toast.makeText(ctx, "Encountered error while getting all conversations display data", Toast.LENGTH_LONG).show();
        Intent loginActivty = new Intent(ctx, LoginActivity.class);
        loginActivty.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(loginActivty);
    }
}
