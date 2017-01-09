package wayt.com.whatareyourthoughts.responses;

public class ConversationResponse {

	private Integer id;
	private String subject;
	private String slug;
	private Boolean draft;
	private String sourceLink;
	
	public ConversationResponse(){}
	public ConversationResponse(Integer id, String subject, String slug, Boolean draft, String sourceLink){
		this.id = id;
		this.subject = subject;
		this.slug = slug;
		this.draft = draft;
		this.sourceLink = sourceLink;
	}
	
	public Integer getId() {
		return id;
	}
	
	public String getSubject() {
		return subject;
	}
	
	public String getSlug() {
		return slug;
	}
	
	public Boolean getDraft() {
		return draft;
	}

	public String getSourceLink() {
		return sourceLink;
	}
}
