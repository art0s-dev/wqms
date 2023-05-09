package crawler.sitemap.validator;

import java.net.URL;

import javax.xml.transform.stream.StreamSource;

import crawler.schemes.Scheme;
import crawler.sitemap.SeoMap;

public class StandardSeoMapValidator implements SeoMapValidator {

	private Scheme scheme;
	
	public void setScheme(Scheme scheme) {
		this.scheme = scheme;
	}

	public boolean isValidSitemap(URL url) {
		var schemeIsNotSet = scheme == null;
		if(schemeIsNotSet) {
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
	
}
