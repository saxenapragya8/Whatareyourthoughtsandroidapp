package wayt.com.whatareyourthoughts.network.Listeners;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Pragya on 1/31/2017.
 */

public class AddNewConversationErrorListener implements Response.ErrorListener {
    private Context ctx;

    public AddNewConversationErrorListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(ctx, "Encountered error while adding new conversation. Please check your internet connection", Toast.LENGTH_LONG).show();
    }
}
