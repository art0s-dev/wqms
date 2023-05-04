package crawler.sitemap.validator;

import crawler.schemes.Scheme;
import crawler.sitemap.SeoMap;

public interface SeoMapValidator {
	public SeoMapValidator of(Scheme scheme);
	public boolean isValid(SeoMap sitemap);
}
