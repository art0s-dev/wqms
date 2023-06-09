package crawler.sitemap.validator;

import java.net.URL;

import javax.xml.transform.stream.StreamSource;

import crawler.schemes.Scheme;


public class StandardSeoMapValidator implements SeoMapValidator {

	private Scheme scheme;
	private URL url;
	
	public void setUrl(URL url) {
		this.url = url;
	}
	
	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public boolean isValidSitemap() {
		var schemeOrUrlWereNotSet = checkSchemeAndUrl();
		if(schemeOrUrlWereNotSet) {
			return false;
		}
		
		try 
		{
			var stream = url.openStream();
			var streamSource = new StreamSource(stream);
			scheme.document.newValidator()
				.validate(streamSource);
			
			return true;
		} 
		
		catch (Exception e) 
		{
			return false;
		}
	}

	private boolean checkSchemeAndUrl() {
		return scheme == null || url == null;
	}
	
}
