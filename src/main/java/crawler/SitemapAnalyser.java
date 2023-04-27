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

public class SitemapAnalyser implements CanScanASingleWebPage{
	private Optional<Document> sitemap;
	private final String pathSitemapXsd = "src/main/java/crawler/sitemap_scheme.xsd";
	private final String pathSitemapSitemapIndexXsd = "src/main/java/crawler/sitemap_scheme_siteindex.xsd";
	private String pathToSitemap = "sitemap.xml";
	private String pathToRobotsFile = "robots.txt";
	
	public SitemapAnalyser(String url) {
		this.pathToRobotsFile = url + this.pathToRobotsFile;
		this.pathToSitemap = url + this.pathToSitemap;
		this.sitemap = this.createSitemap(url);
	}
	
	private Optional<Document> createSitemap(String url){
		String pathToRobotsFile = this.pathToRobotsFile;
		String pathToSitemap = this.pathToSitemap;
		
		if(isSitemap(pathToSitemap)) {
			return parseSitemap(pathToSitemap);
		}
		
		pathToSitemap = searchSitemapFromRobotsFile(pathToRobotsFile);
		if(isSitemap(pathToSitemap)) {
			return parseSitemap(pathToSitemap);
		}
		
		return Optional.empty();
	}
	
	private Boolean isSitemap(String url) {
		Boolean sitemapIsNormalSitemap = validateSitemap(url, this.pathSitemapXsd);
		Boolean sitemapHasSiteIndex = validateSitemap(url, this.pathSitemapSitemapIndexXsd);
		
		if(sitemapIsNormalSitemap) {
			return true;
		}
		
		return sitemapHasSiteIndex;
	}
	
	private Boolean validateSitemap(String url, String pathToScheme) {
		try {
			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(new File(pathToScheme));
			Validator validator = schema.newValidator();
			
			URL urlObject = new URL(url);
			InputStream stream = urlObject.openStream();
			StreamSource source = new StreamSource(stream);
			validator.validate(source);
			
			return true;
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
	
	private String searchSitemapFromRobotsFile(String url) {
		try {
			Optional<HttpResponse<String>> response = this.scan(url);
			String file = response.orElseThrow().body();
			String annotation = "Sitemap: ";
			
			int postionOfAnnotation = file.indexOf(annotation); 
			String foundString = file.substring(postionOfAnnotation);
			String[] stringList = foundString.split(" ");
			String linkToSitemap = stringList[1];
			
			return linkToSitemap;
		} catch (Exception e) {
			return "";
		}
	}

	public Optional<Document> getSitemap() { return this.sitemap; }
}
