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
	
	public Scheme loadSitemap() { 
		return new Sitemap(Sitemap.path, load(Sitemap.path));
	}
	
	public Scheme loadSiteindex() { 
		return new Siteindex(Siteindex.path, load(Siteindex.path));
	}

	private Schema load(String path){
		try {
			var factory = SchemaFactory.newInstance(asXSD);
			return factory.newSchema(new File(path));
		}
		
		catch(SAXException e)
		{
			return null;
		}
	}
	
}