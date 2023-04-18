package crawler;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.Optional;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Document;

public class SitemapAnalyzer implements CanProcessWebPages{
	private Optional<Document> sitemap;
	private final String pathToSitemapXsd = "src/main/java/crawler/sitemap_scheme.xsd";
	private String pathToSitemap = "/sitemap.xml";
	private String pathToRobotsFile = "/robots.txt";
	
	public SitemapAnalyzer(String url) {
		this.pathToRobotsFile = url + this.pathToRobotsFile;
		this.pathToSitemap = url + this.pathToSitemap;
		this.sitemap = this.createSitemap(url);
	}
	
	private Optional<Document> createSitemap(String url){
		String pathToRobotsFile = this.pathToRobotsFile;
		String pathToSitemap = this.pathToSitemap;
		
		if(this.isSitemapThere(pathToSitemap)) {
			return parseSitemap(pathToSitemap);
		}
		
		if(this.isRobotsFileThere(pathToRobotsFile)) {
			pathToSitemap = this.searchSitemapFromRobotsFile(pathToRobotsFile);
		}
		if(this.isSitemapThere(pathToSitemap)) {
			return parseSitemap(pathToSitemap);
		}
		
		pathToSitemap = this.searchSitemapInOtherLocations();
		if(this.isSitemapThere(pathToSitemap)) {
			return parseSitemap(pathToSitemap);
		}
		return Optional.empty();
	}
	
	
	private Boolean isSitemapThere(String url) {
		try {
			URL urlObject = new URL(url);
			Optional<HttpResponse<String>> response = this.scan(url);
			String markup = response.orElseThrow().body();
			return markup.contains("<urlset");
		} catch (Exception e) {
			return false;
		}
	}
	
	private Optional<Document> parseSitemap(String url){
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		try {
			DocumentBuilder builder = instance.newDocumentBuilder();
			URL urlObject = new URL(url);
			InputStream stream = urlObject.openStream();
			Document sitemap = builder.parse(stream);
			return Optional.of(sitemap);
		} catch (Exception e) {
			return Optional.empty();
		}
	}
	
	
	private Boolean isRobotsFileThere(String url) {
		return false;
	}
	
	private String searchSitemapFromRobotsFile(String url) {
		return "";
	}
	
	private String searchSitemapInOtherLocations(){
		return "";
	}
	
	
	private Boolean isSitemapValid(String url) {
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(this.pathToSitemapXsd));
			Validator validator = schema.newValidator();
			
			URL urlObject = new URL(url);
			InputStream stream = urlObject.openStream();
			validator.validate(new StreamSource(stream));
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}

	public Optional<Document> getSitemap() { return this.sitemap; }
}
