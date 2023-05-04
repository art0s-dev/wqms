package crawler.schemes.loader;

import java.util.List;
import javax.xml.XMLConstants;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import org.xml.sax.SAXException;
import crawler.schemes.Scheme;
import crawler.schemes.Siteindex;
import crawler.schemes.Sitemap;
import java.io.File;

public final class StaticSchemeLoader implements SchemeLoader{
	private String asXSD = XMLConstants.W3C_XML_SCHEMA_NS_URI;

	public List<Scheme> load(){
		try {
			var siteindex = new Siteindex(Siteindex.path, create(Siteindex.path));
			var sitemap = new Sitemap(Sitemap.path, create(Sitemap.path));
			return List.of(siteindex, sitemap);
		}
		
		catch(SAXException e)
		{
			return List.of();
		}
	}
	
	private Schema create(String path) throws SAXException {
		var factory = SchemaFactory.newInstance(asXSD);
		return factory.newSchema(new File(path));
	}
	
	
	
}
