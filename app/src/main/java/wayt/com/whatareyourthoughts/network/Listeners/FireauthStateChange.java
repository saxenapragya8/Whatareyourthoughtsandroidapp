package wayt.com.whatareyourthoughts.network.Listeners;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Pragya on 2/20/2017.
 */

public class FireauthStateChange implements FirebaseAuth.AuthStateListener {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {
            // User is signed in
            String displayName = user.getDisplayName();
            String email = user.getEmail();
            String userId = user.getUid();
            Log.d("FireauthStateChange", "onAuthStateChanged:signed_in:" + user.getUid());
        } else {
            // User is signed out
            Log.d("FireauthStateChange", "onAuthStateChanged:signed_out");
        }
        // [START_EXCLUDE]
//        updateUI(user);
        // [END_EXCLUDE]
    }
}
