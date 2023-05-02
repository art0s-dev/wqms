package crawler.schemes;

import javax.xml.validation.Schema;

public final class Siteindex extends Scheme {
	
	private static String path = "schemes/sitemap.xsd.xml";

	public Siteindex(String name, Schema document) {
		super(name, path, document);
	}

}
