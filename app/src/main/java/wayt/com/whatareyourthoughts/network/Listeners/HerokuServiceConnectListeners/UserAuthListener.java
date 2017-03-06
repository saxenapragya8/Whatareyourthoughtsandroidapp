package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONObject;

import wayt.com.whatareyourthoughts.ErrorActivity;
import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;
import wayt.com.whatareyourthoughts.network.HttpRequestSender;
import wayt.com.whatareyourthoughts.responses.UserAuthResponse;

/**
 * Created by Pragya on 1/8/2017.
 */

public class UserAuthListener implements Response.Listener<JSONObject> {
    private Context ctx;

    public UserAuthListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onResponse(JSONObject response) {
        try {
            Integer userId = response.getInt("userId");
            Boolean isUser = response.getBoolean("user");
            Log.d("httpResponse", "Response: " + userId + " " + isUser);
            if(isUser){
                storeUserIdInPrefs(userId, ctx);
                getAllUserDisplayData(userId, ctx);
                sendRegistrationIdToServer(userId);
            } else{
                Toast.makeText(ctx, "Username password not valid", Toast.LENGTH_LONG).show();
            }
        }catch(Exception e){
            Toast.makeText(ctx, "Could not get a valid user id or regid update response", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    private void getAllUserDisplayData(Integer userId, Context ctx) {
        HttpRequestSender.getInstance(ctx).getUserConversationsData(userId, ctx);
    }

    private void sendRegistrationIdToServer(Integer userId) {
        String regId = getRegToken();
        if(!regId.isEmpty())
            HttpRequestSender.getInstance(ctx).updateRegId(userId, regId, ctx);
    }

    private void storeUserIdInPrefs(int userId, Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(ctx.getString(R.string.user_id_field), userId);
        editor.apply();
    }

    private String getRegToken(){
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.firebase_token), Context.MODE_PRIVATE);
        return sharedPref.getString(ctx.getString(R.string.firebase_token), "");
    }
}
