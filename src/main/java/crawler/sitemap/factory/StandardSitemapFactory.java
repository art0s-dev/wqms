package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

import crawler.schemes.Scheme;
import crawler.sitemap.Sitemap;

import static java.util.Optional.empty;

import java.io.IOException;
import java.io.InputStream;

public final class StandardSitemapFactory implements SeoMapFactory{
	
	private List<Scheme> schemes;
	private URL url;
	
	public Optional<Sitemap> build(){
		var urlOrSchemesWereNotSet = checkNull();
		if(urlOrSchemesWereNotSet) {
			return empty();
		}
		
		var optionOfSource = createStreamSource();
		var sourceCannotBeOpened = optionOfSource.isEmpty();
		if(sourceCannotBeOpened) {
			return empty();
		}
		
		var source = optionOfSource.orElseThrow();
		var xmlIsNotValid = !sitemapIsValid(source);
		if(xmlIsNotValid){
			return empty();
		}
		
		var optionOfSitemap = parseSitemap();
		var sitemapCannotBeOpened = optionOfSitemap.isEmpty();
		if(sitemapCannotBeOpened) {
			return empty();
		}
		
		/**
		 * TODO: We've got a little problem:
		 * every sitemap index has a sitemap 
		 * we have to change the api
		 * and check during creation of scheme if its a sitemap index
		 * oder a sitemap and then we've got to implement some 
		 * polymorphism on every scheme to solve creation
		 * so for example we need a scheme<sitemap> and scheme
		 * <siteindex>
		 */
		
		

		return empty();
	}

	private Optional<Document> parseSitemap(){
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder = instance.newDocumentBuilder();
			InputStream stream = this.url.openStream();
			Document sitemap = builder.parse(stream);
			
			return Optional.of(sitemap);
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			return Optional.empty();
		}
	}
	
	private boolean sitemapIsValid(StreamSource source) {
		for (Scheme scheme: this.schemes) {
			if(validate(scheme,source)) {
				return true;
			}
		}
		return false;
	}
	
	private boolean validate(Scheme scheme, StreamSource source) {
		try 
		{
			scheme.document.newValidator().validate(source);
			return true;
		} 
		
		catch (Exception e) 
		{
			e.printStackTrace();
			return false;
		}
	}
	
	private boolean checkNull() {
		return this.url == null 
			|| this.schemes == null;
	}
	
	public void setUrl(URL url) { this.url = url; }
	public void setSchemes(List<Scheme> schemes) {
		this.schemes = schemes;
	}
	
	private Optional<StreamSource> createStreamSource() {
		try 
		{
			var stream = url.openStream();
			return Optional.of(new StreamSource(stream));
		}
		
		catch(IOException e) 
		{
			return empty();
		}

	}
}
