package crawler.schemes;

import javax.xml.validation.Schema;

public final class Sitemap extends Scheme {
	
	private static String path = "schemes/sitemap.xsd.xml";
	
	public Sitemap(String name, Schema document) {
		super(name, path, document);
	}
}
