package crawler.extractor;

import java.util.List;

public final class Meta {
	public final String body;
	public final String title;
	public final String description;
	public final List<String> keywords;
	
	public Meta(
		String body, 
		String title, 
		String description, 
		List<String> keywords
	) {
		this.body = body;
		this.title = title;
		this.description = description;
		this.keywords = keywords;
	}

}
