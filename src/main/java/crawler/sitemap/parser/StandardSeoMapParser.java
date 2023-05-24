package crawler.sitemap.parser;

import java.io.InputStream;
import java.net.URL;
import java.util.Optional;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;

import static java.util.Optional.empty;

public final class StandardSeoMapParser implements SeoMapParser {
	
	private URL url;

	public void setUrl(URL url) {
		this.url = url;
	}

	public Optional<Document> parse() {
		
		var urlWasNotSet = url == null;
		if(urlWasNotSet) {
			return empty();
		}
		
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder builder = instance.newDocumentBuilder();
			disbaleErrorHandler(builder);
			InputStream stream = url.openStream();
			Document sitemap = builder.parse(stream);
			
			return Optional.of(sitemap);
		} 
		
		catch (Exception e) 
		{
			return empty();
		}
	}
	
	/**
	 * disableErrorHandler
	 * The default handler prints fatals out to the std:err
	 * which can be considered an unwanted side effect
	 * @param builder
	 */
	private void disbaleErrorHandler(DocumentBuilder builder) {
		builder.setErrorHandler(null);
	}
	
}
