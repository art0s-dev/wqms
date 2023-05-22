package crawler.sitemap.validator;

import java.net.URL;

import crawler.schemes.Scheme;

public interface SeoMapValidator {
	public void setScheme(Scheme scheme);
	public void setUrl(URL url);
	public boolean isValidSitemap();
}
