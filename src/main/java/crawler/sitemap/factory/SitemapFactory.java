
package crawler.sitemap.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

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
		var documentWrapper = parser.parse();
		var parserWasNotSuccessfull = documentWrapper.isEmpty();
		
		if(sitemapIsNotValid | parserWasNotSuccessfull) {
			return List.of();
		}
		
		var document = documentWrapper.orElseThrow();
		return createLinkList(document);
	}

	private List<Optional<URL>> createLinkList(Document document) {
		List<Optional<URL>> list = new ArrayList<>(List.of());
		var root = document.getFirstChild();
		var numberOfLinks = root.getChildNodes().getLength();
		
		for(int i = 0; i < numberOfLinks; i++) {
			var node = root.getChildNodes().item(i);
			var numberOfSubnodes = node.getChildNodes().getLength();
			
			for(int j = 0; j < numberOfSubnodes; j++) {
				var subnode = node.getChildNodes().item(j);
				
				var nodeIsNotALink = subnode.getNodeName() != "loc";
				if(nodeIsNotALink) {
					continue;
				}
				
				list.add(createLink(subnode));
			}
		}
		return list;
	}
	
	private Optional<URL> createLink(Node node){
		try 
		{
			var url = node.getTextContent();
			var link = new URL(url);
			return Optional.of(link);
		}
		
		catch(MalformedURLException e)
		{
			return Optional.empty();
		}
	}

}