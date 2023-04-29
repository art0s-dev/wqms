package crawler.extractor;

import java.util.Arrays;
import crawler.htmlParser.AttributeParser;
import java.util.Optional;
import java.util.stream.Collectors;

import crawler.website.Website;
import crawler.htmlParser.TagParser;

public final class StandardMetaExtractor implements MetaExtractor {
	
	Website website;
	String titleTag = "title";
	String descriptionAttribute = "description";
	
	public Optional<Meta> build() {
		
		var website = this.website;
		var websiteWasNotSet = website == null;
		
		if(websiteWasNotSet) {
			return Optional.empty();
		} 
		
		var title = new TagParser(website)
				.extract(titleTag); 
		
		var description = new AttributeParser(website)
				.extract(descriptionAttribute);

		var meta = new Meta(title,description);
		
		return Optional.of(meta);
	}
 
	public void setWebsite(Website website) {
		this.website = website;
	}
}
