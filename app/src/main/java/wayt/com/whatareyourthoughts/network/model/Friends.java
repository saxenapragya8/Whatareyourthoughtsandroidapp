package wayt.com.whatareyourthoughts.network.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by Pragya on 3/5/2017.
 */

@IgnoreExtraProperties
public class Friends {
    private String friendName;
    private String friendId;

    public String getFriendName() {
        return friendName;
    }

    public void setFriendName(String friendName) {
        this.friendName = friendName;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}
