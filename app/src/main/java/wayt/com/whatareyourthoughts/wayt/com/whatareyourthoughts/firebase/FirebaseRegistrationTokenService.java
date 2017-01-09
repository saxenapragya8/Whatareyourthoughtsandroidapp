package wayt.com.whatareyourthoughts.wayt.com.whatareyourthoughts.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.HttpRequestSender;

/**
 * Created by Pragya on 12/2/2016.
 */

public class FirebaseRegistrationTokenService extends FirebaseInstanceIdService {

    private static final String TAG = "FirebaseRegToken";

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
        int userId = getUserIdFromPrefs();
        if(userId != 0)
            HttpRequestSender.getInstance(this).updateRegId(userId, token, this);
        addToSharedPrefs(token);
    }

    private void addToSharedPrefs(String token) {
        Context ctx = this.getApplicationContext();
        SharedPreferences sharedPref = ctx.getSharedPreferences(ctx.getString(R.string.firebase_token), Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ctx.getString(R.string.firebase_token), token);
        editor.apply();
    }

    private int getUserIdFromPrefs(){
        SharedPreferences sharedPref = this.getApplicationContext().getSharedPreferences(getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(getString(R.string.user_id_field), 0);
    }
}
