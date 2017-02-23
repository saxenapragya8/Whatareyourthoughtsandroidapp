package wayt.com.whatareyourthoughts.network;

/**
 * Created by Pragya on 12/2/2016.
 */

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;
import org.springframework.web.util.UriComponentsBuilder;

import wayt.com.whatareyourthoughts.R;
import wayt.com.whatareyourthoughts.network.Listeners.AddNewCommentErrorListener;
import wayt.com.whatareyourthoughts.network.Listeners.AddNewCommentListener;
import wayt.com.whatareyourthoughts.network.Listeners.AddNewConversationErrorListener;
import wayt.com.whatareyourthoughts.network.Listeners.AddNewConversationListener;
import wayt.com.whatareyourthoughts.network.Listeners.GetAllDisplayDataErrorListener;
import wayt.com.whatareyourthoughts.network.Listeners.GetAllDisplayDataListener;
import wayt.com.whatareyourthoughts.network.Listeners.LogErrorListener;
import wayt.com.whatareyourthoughts.network.Listeners.UpdateRegistrationIdListener;
import wayt.com.whatareyourthoughts.network.Listeners.UserAuthListener;
import wayt.com.whatareyourthoughts.responses.UserAuthResponse;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

public class HttpRequestSender {

    private static final String WAYT_SERVER = "https://evil-pumpkin-78760.herokuapp.com/rest/";
    private static final String USER_AUTH_PATH = "user/addNewUser";
    private static final String UPDATE_REGID_PATH = "regid/updateregid";
    private static final String ALL_DISPLAY_DATA_PATH = "displaydata/getdata";
    private static final String ADD_NEW_CONVERSATION_DATA_PATH = "conversations/addconversation";
    private static final String ADD_COMMENT_PATH = "comments/addcomment";
    private RequestQueue queue;
    private static HttpRequestSender instance;

    private HttpRequestSender(Context context)
    {
        queue = Volley.newRequestQueue(context.getApplicationContext());
        //other stuf if you need
    }

    public static synchronized HttpRequestSender getInstance(Context context)
    {
        if (null == instance)
            instance = new HttpRequestSender(context);
        return instance;
    }

    public void authenticateUser(final String email, final String userName, final String userId, final Context ctx) {
                try {
                    UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(WAYT_SERVER + USER_AUTH_PATH)
                            // Add query parameter
                            .queryParam("username", userName).queryParam("email", email).queryParam("userId",userId);
                    JsonObjectRequest usrAuthReq = new JsonObjectRequest (Request.Method.GET, urlBuilder.toUriString(), null, new UserAuthListener(ctx), new LogErrorListener(ctx));
// Add the request to the RequestQueue.
                    queue.add(usrAuthReq);
                } catch (Exception e) {
                    Toast.makeText(ctx.getApplicationContext(), "Error authenticating user", Toast.LENGTH_LONG).show();
                }
    }

    public void updateRegId(int usrId, String regId, Context ctx) {
        try {
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(WAYT_SERVER + UPDATE_REGID_PATH).queryParam("userId", usrId).queryParam("registrationId", regId);
            JsonObjectRequest updateRegIdReq = new JsonObjectRequest (Request.Method.POST, urlBuilder.toUriString(), null, new UpdateRegistrationIdListener(ctx), new LogErrorListener(ctx)){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            queue.add(updateRegIdReq);
        } catch (Exception e) {
            Toast.makeText(ctx.getApplicationContext(), "Error updating registration id", Toast.LENGTH_LONG).show();
        }
    }

    public void getUserConversationsData(int usrId, Context ctx) {
        try {
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(WAYT_SERVER + ALL_DISPLAY_DATA_PATH).queryParam("usrId", usrId);
            JsonObjectRequest getAllDisplayDataReq = new JsonObjectRequest (Request.Method.POST, urlBuilder.toUriString(), null, new GetAllDisplayDataListener(ctx), new GetAllDisplayDataErrorListener(ctx)){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            queue.add(getAllDisplayDataReq);
        } catch (Exception e) {
            Toast.makeText(ctx.getApplicationContext(), "Error getting user conversation data", Toast.LENGTH_LONG).show();
        }
    }

    public void addNewConversation(int usrId, Context ctx,
                                   String subject, String link,
                                   String recipientIds,
                                   String content) {
        try {
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(WAYT_SERVER + ADD_NEW_CONVERSATION_DATA_PATH)
                    .queryParam("userId", usrId)
                    .queryParam("subject", subject)
                    .queryParam("link", link)
                    .queryParam("recipientIds", recipientIds)
                    .queryParam("content", content);
            JsonObjectRequest addNewConversationRequest = new JsonObjectRequest (Request.Method.POST, urlBuilder.toUriString(), null, new AddNewConversationListener(ctx), new AddNewConversationErrorListener(ctx)){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            queue.add(addNewConversationRequest);
        } catch (Exception e) {
            Toast.makeText(ctx.getApplicationContext(), "Error sending add new conversation request", Toast.LENGTH_LONG).show();
        }
    }

    public void addNewComment(int usrId, int convId,Context ctx,
                                   String content) {
        try {
            UriComponentsBuilder urlBuilder = UriComponentsBuilder.fromUriString(WAYT_SERVER + ADD_COMMENT_PATH)
                    .queryParam("convId", convId)
                    .queryParam("userId", usrId)
                    .queryParam("content", content);
            JsonObjectRequest addNewConversationRequest = new JsonObjectRequest (Request.Method.POST, urlBuilder.toUriString(), null, new AddNewCommentListener(ctx), new AddNewCommentErrorListener(ctx)){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  params = new HashMap<>();
                    params.put("Content-Type", "application/json");
                    params.put("Accept", "application/json");
                    return params;
                }
            };
            queue.add(addNewConversationRequest);
        } catch (Exception e) {
            Toast.makeText(ctx.getApplicationContext(), "Error sending add new conversation request", Toast.LENGTH_LONG).show();
        }
    }
}
