
package crawler.sitemap.factory;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
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
	private static final String linkNodeName = "loc";
	
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
		var documentWrapper = parser.parse();
		
		var documentIsNotValid = checkValidationRules(documentWrapper);
		if(documentIsNotValid) {
			return List.of();
		}
		
		var document = documentWrapper.orElseThrow();
		return createLinkList(document);
	}


	private List<Optional<URL>> createLinkList(Document document) {
		var list = createEmptyList();
		var root = document.getFirstChild();
		var numberOfLinks = howManyChildren(root);
		
		for(int i = 0; i < numberOfLinks; i++) {
			var node = root.getChildNodes().item(i);
			var numberOfSubnodes = howManyChildren(node);
			
			for(int j = 0; j < numberOfSubnodes; j++) {
				var subnode = node.getChildNodes().item(j);
				
				var nodeIsNotALink = subnode.getNodeName() != linkNodeName;
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
	
	private boolean checkValidationRules(Optional<Document> documentWrapper) {
		return documentWrapper.isEmpty() 
				|| !validator.isValidSitemap() 
				|| url == null;
	}
	
	private int howManyChildren(Node node) {
		return node.getChildNodes().getLength();
	}
	
	private List<Optional<URL>> createEmptyList(){
		return new ArrayList<>(List.of());
	}

}