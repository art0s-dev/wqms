package crawler.sitemap.factory;

import java.net.MalformedURLException;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.w3c.dom.Node;

import crawler.schemes.loader.StaticSchemeLoader;

public class SiteindexFactory implements SeoMapFactory {

	private URL url;
	private SitemapFactory sitemapFactory;
	private String LINK_NODE_NAME = "loc";
	
	public SiteindexFactory(SitemapFactory sitemapFactory) {
		this.sitemapFactory = sitemapFactory;
	}
	
	public void setUrl(URL url) {
		this.url = url;
		sitemapFactory.setUrl(url);
	}

	public Sitemap build() {
		var documentWrapper = sitemapFactory.getParser().parse();
		var documentIsNotValid = sitemapFactory.checkValidationRules(documentWrapper);
		if(documentIsNotValid) {
			return new Sitemap(List.of());
		}
		
		var scheme = new StaticSchemeLoader().loadSitemap();
		sitemapFactory.getValidator().setScheme(scheme);
		
		var document = documentWrapper.orElseThrow();
		var numberOfSites = document.getChildNodes().getLength();
		var list = sitemapFactory.createEmptyList();
		
		for(int i = 0; i < numberOfSites; i++) {
			var node = document.getChildNodes().item(i);
			var numberOfChildNodes = node.getChildNodes().getLength();
			
			for(int j = 0; j < numberOfChildNodes; j++) {
				var subnode = node.getChildNodes().item(j);
				
				var nodeIsNotALink = subnode.getNodeName().equals(LINK_NODE_NAME);
				if(nodeIsNotALink) {
					continue;
				}

				var link = sitemapFactory.createLink(subnode);
				list.add(link);
			}
		}
		
		var linkList = list.parallelStream()
			.filter(Optional::isPresent)
			.map(this::createLinkList)
			.flatMap(List::stream)
			.toList();

		return new Sitemap(linkList);
		
	}
	
	private List<Optional<URL>> createLinkList(Optional<URL> link){
		setUrl(link.orElseThrow());
		return sitemapFactory.build().linkList();
	}

}
