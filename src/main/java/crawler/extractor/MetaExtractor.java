package crawler.extractor;

import java.util.Optional;

import crawler.website.Website;

public interface MetaExtractor {
	public Optional<Meta> build();

	public void setWebsite(Website website);
}
