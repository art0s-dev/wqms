package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import crawler.sitemap.parser.SeoMapParser;
import crawler.sitemap.validator.SeoMapValidator;


public final class SitemapFactory implements SeoMapFactory {

	private URL url;
	private SeoMapValidator validator;
	private SeoMapParser parser;
	
	public SitemapFactory(SeoMapValidator validator, SeoMapParser parser) {
		this.validator = validator;
		this.parser = parser;
	}
	
	public void setUrl(URL url) {
		this.url = url;
		this.parser.setUrl(url);
		this.validator.setUrl(url);
	}

	public List<Optional<URL>> build() {
		var urlWasNotSet = url == null;
		if(urlWasNotSet) {
			return List.of();
		}
		
		var sitemapIsNotValid = !validator.isValidSitemap();
		var document = parser.parse();
		var parserWasNotSuccessfull = document.isEmpty();
		
		if(sitemapIsNotValid | parserWasNotSuccessfull) {
			return List.of();
		}		
		
		return List.of();
	}


}
