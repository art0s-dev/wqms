package crawler.extractor;

public final class Meta {
	//public final String body;
	public final String title;
	public final String description;
	//public final List<String> keywords;
	
	public Meta(
		String title,
		String description 
		//List<String> keywords
	) {
		this.title = title;
		this.description = description;
		//this.keywords = keywords;
	}

}
