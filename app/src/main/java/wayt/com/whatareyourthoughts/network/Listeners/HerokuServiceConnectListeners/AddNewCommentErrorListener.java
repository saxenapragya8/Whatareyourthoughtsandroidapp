package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Pragya on 2/12/2017.
 */

public class AddNewCommentErrorListener implements Response.ErrorListener {
    private Context ctx;

    public AddNewCommentErrorListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Toast.makeText(ctx, "Encountered error while adding new comment", Toast.LENGTH_LONG).show();
    }
}
