package crawler;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import crawler.extractor.Meta;

public interface CanCrawlAWholeWebspace {
	public static List<Meta> crawl(String url){
		List<Meta> list = Collections.emptyList();
		//TODO: Implement the logic correctly
		return list;
		
		/**
		Optional<Document> sitemap = new SitemapAnalyser(url).getSitemap();
		
		if(sitemap.isEmpty()) {
			return list;
		}
		
		Document xml = sitemap.orElseThrow();
		Element element = xml.getDocumentElement();
		NodeList nodeList = element.getChildNodes();
		
		
		for(int i = 0; i < nodeList.getLength(); i++) {
			Node node = nodeList.item(i);
			NodeList child = node.getChildNodes();
			
			
			String link = child.item(0).getTextContent();

			Optional<Meta> meta = new WebsiteScanner(link).getMeta();
			if(meta.isPresent()) {
				list.add(meta.orElseThrow());
			}
		}
		
		return list;
		*/
	}
}
