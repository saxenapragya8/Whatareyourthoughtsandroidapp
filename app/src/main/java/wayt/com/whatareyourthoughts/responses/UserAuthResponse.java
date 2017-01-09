package wayt.com.whatareyourthoughts.responses;

/**
 * Created by Pragya on 12/2/2016.
 */
import java.io.Serializable;

@SuppressWarnings("serial")
public final class UserAuthResponse implements Serializable{

    private int userId;
    private boolean isUser;

    public UserAuthResponse(){}

    public UserAuthResponse(int userId, boolean isUser){
        this.userId = userId;
        this.isUser = isUser;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isUser() {
        return isUser;
    }
}
