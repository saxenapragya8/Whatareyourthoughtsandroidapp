package wayt.com.whatareyourthoughts.network.Listeners.HerokuServiceConnectListeners;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by Pragya on 1/8/2017.
 */

public class LogErrorListener implements Response.ErrorListener {

    private Context ctx;

    public LogErrorListener(Context ctx){
        super();
        this.ctx = ctx;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        Log.d("ErrorListener", error.toString() );
        error.printStackTrace();
        Toast.makeText(ctx, "Encountered error while connecting to WAYT server", Toast.LENGTH_LONG).show();
    }
}
