package wayt.com.whatareyourthoughts;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.app.LoaderManager.LoaderCallbacks;

import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import wayt.com.whatareyourthoughts.network.HttpRequestSender;
import wayt.com.whatareyourthoughts.network.Listeners.FireauthStateChange;
import wayt.com.whatareyourthoughts.network.Listeners.GoogleSigninFailedListener;
import wayt.com.whatareyourthoughts.network.NetworkAvailabilityChecker;
import wayt.com.whatareyourthoughts.responses.UserAuthResponse;

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity {

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;
    private HttpRequestSender requestSender;
    Context ctx;
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FireauthStateChange mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .requestScopes(new Scope(Scopes.EMAIL), new Scope(Scopes.PROFILE), new Scope(Scopes.PLUS_LOGIN), new Scope(Scopes.PLUS_ME))
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, new GoogleSigninFailedListener(this) /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        mAuth = FirebaseAuth.getInstance();
//        // TODO refactor
//        if (getUserId() != 0) {
//            HttpRequestSender.getInstance(this).getUserConversationsData(getUserId(), this);
//            Intent errorIntent = new Intent(this, ShowAllConversationsActivity.class);
//            startActivity(errorIntent);
//        };

        checkGooglePlayServicesAvailability();
//        // Set up the login form.
//        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
//        mPasswordView = (EditText) findViewById(R.id.password);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Button mEmailSignInButton = (Button) findViewById(R.id.email_sign_in_button);
//        this.ctx = getApplicationContext();
//        requestSender = HttpRequestSender.getInstance(ctx);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
//                // Store values at the time of the login attempt.
//                String email = mEmailView.getText().toString();
//                String password = mPasswordView.getText().toString();
//
//                try {
//                    new LoginTask(email, password).execute().get();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                } catch (ExecutionException e) {
//                    e.printStackTrace();
//                }
            signIn();
            }
        });

//        mLoginFormView = findViewById(R.id.login_form);
//        mProgressView = findViewById(R.id.login_progress);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
                HttpRequestSender.getInstance(this).authenticateUser(account.getEmail(), account.getDisplayName(), account.getId(), this);
                //TODO Add sending user name and email to users table
            } else {
                Toast.makeText(ctx, "Google Sign in failed", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(this.getClass().toString(), "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(this.getClass().toString(), "signInWithCredential:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(this.getClass().toString(), "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        mAuth.signOut();

        // Google sign out
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(@NonNull Status status) {
//                        updateUI(null);
                    }
                });
    }

    private Integer getUserId(){

        SharedPreferences sharedPref = this.getSharedPreferences(this.getString(R.string.user_id_field), Context.MODE_PRIVATE);
        return sharedPref.getInt(this.getString(R.string.user_id_field), 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkGooglePlayServicesAvailability();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuthListener = new FireauthStateChange();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void checkGooglePlayServicesAvailability() {
        Integer resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (resultCode != ConnectionResult.SUCCESS) {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(resultCode, this, 0);
            if (dialog != null) {
                //This dialog will help the user update to the latest GooglePlayServices
                dialog.show();
            }
            Intent errorIntent = new Intent(this, ErrorActivity.class);
            startActivity(errorIntent);
        }
    }

//    private class LoginTask extends AsyncTask<String, Void, String> {
//        String email;
//        String passwd;
//
//        LoginTask(String email, String passwd) {
//            this.email = email;
//            this.passwd = passwd;
//        }
//
//        /**
//         * The system calls this to perform work in a worker thread and
//         * delivers it the parameters given to AsyncTask.execute()
//         */
//        protected String doInBackground(String... urls) {
//            if (NetworkAvailabilityChecker.isNetworkAvailable(ctx)) {
////                showProgress(true);
//                requestSender.authenticateUser(email, passwd, ctx);
//            } else {
//                Toast.makeText(ctx, "check your internet connection. The app doesnt work without a net connection", Toast.LENGTH_LONG).show();
//            }
//            return null;
//        }
//
//        /**
//         * The system calls this to perform work in the UI thread and delivers
//         * the result from doInBackground()
//         */
//        protected void onPostExecute() {
//            Log.d("asyncTask", "Done with the task");
//        }
//    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
//    private void attemptLogin(String email, String password) {
//        if (requestSender != null) {
//            return;
//        }
//
//        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
//            Log.d("loginActivity", "Not good email and passwd");
//            Toast.makeText(this, " Enter valid email and password", Toast.LENGTH_LONG).show();
//        } else {
//
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            if (NetworkAvailabilityChecker.isNetworkAvailable(this)) {
////                showProgress(true);
//                requestSender.authenticateUser(email, password, this);
//            } else {
//                Toast.makeText(this, "check your internet connection. The app doesnt work without a net connection", Toast.LENGTH_LONG).show();
//            }
//        }
//    }

}
