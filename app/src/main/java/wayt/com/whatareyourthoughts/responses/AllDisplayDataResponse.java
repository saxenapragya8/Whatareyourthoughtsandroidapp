package wayt.com.whatareyourthoughts.responses;

import java.util.List;

public class AllDisplayDataResponse {

	List<ParticipationResponse> participations;
	List<ConversationResponse> conversations;
	List<CommentResponse> comments;
	
	public AllDisplayDataResponse(){}
	public AllDisplayDataResponse(List<ParticipationResponse> participations, List<ConversationResponse> conversations, List<CommentResponse> comments){
		this.participations = participations;
		this.conversations = conversations;
		this.comments = comments;
	}

	public List<ParticipationResponse> getParticipations() {
		return participations;
	}

	public List<ConversationResponse> getConversations() {
		return conversations;
	}

	public List<CommentResponse> getComments() {
		return comments;
	}
}
