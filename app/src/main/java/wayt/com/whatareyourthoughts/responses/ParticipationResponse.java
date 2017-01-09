package wayt.com.whatareyourthoughts.responses;

public class ParticipationResponse {

	Integer id;
	Integer conversationId;
	boolean read;
	boolean important;
	int othersCount;
	boolean mute;
	
	public ParticipationResponse(){}
	public ParticipationResponse(Integer id, Integer conversationId, boolean read, boolean imp, int othersCount, boolean mute){
		this.id = id;
		this.conversationId = conversationId;
		this.read = read;
		this.important = imp;
		this.othersCount = othersCount;
		this.mute = mute;
	}

	public Integer getId() {
		return id;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public boolean isRead() {
		return read;
	}

	public boolean isImportant() {
		return important;
	}

	public int getOthersCount() {
		return othersCount;
	}

	public boolean isMute() {
		return mute;
	}
}
