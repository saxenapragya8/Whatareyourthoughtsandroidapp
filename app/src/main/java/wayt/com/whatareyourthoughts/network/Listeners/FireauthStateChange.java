package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;

import wayt.com.whatareyourthoughts.AddNewConversationActivity;
import wayt.com.whatareyourthoughts.ShowAllConversationsActivity;
import wayt.com.whatareyourthoughts.network.RealtimeDbWriter;
import wayt.com.whatareyourthoughts.network.model.UserData;

/**
 * Created by Pragya on 2/20/2017.
 */

public class FireauthStateChange implements FirebaseAuth.AuthStateListener {

    private Context ctx;
    public FireauthStateChange(Context ctx){
        this.ctx = ctx;
    }

    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
//            Intent intent = new Intent(ctx, AddNewConversationActivity.class);
//            ctx.startActivity(intent);
        } else {
            // User is signed out
            Log.d("FireauthStateChange", "onAuthStateChanged:signed_out");
        }
        // [START_EXCLUDE]
//        updateUI(user);
        // [END_EXCLUDE]
    }
}
