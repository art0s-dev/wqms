package crawler.extractor;

import crawler.htmlParser.AttributeParser;
import java.util.Optional;
import static java.util.Optional.empty;

import crawler.website.Website;
import crawler.htmlParser.TagParser;

public final class StandardMetaExtractor implements MetaExtractor {

	Website website;
	String titleTag = "title";
	String descriptionAttribute = "description";

	public Optional<Meta> build() {
		var websiteWasNotSet = website == null;

		if (websiteWasNotSet) {
			return empty();
		}

		var title = new TagParser(website).extract(titleTag);
		var description = new AttributeParser(website)
				.extract(descriptionAttribute);

		var meta = new Meta(title, description);
		return Optional.of(meta);
	}

	public void setWebsite(Website website) {
		this.website = website;
	}
}
