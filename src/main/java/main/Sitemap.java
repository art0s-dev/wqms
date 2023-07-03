package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.function.Function;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Sitemap {
	private static String asXSD = XMLConstants.W3C_XML_SCHEMA_NS_URI;
	
	public static List<String> create(URL url) throws 
	ParserConfigurationException,
	IOException, 
	SAXException,
	SAXParseException,
	Exception
	{
		var factory = SchemaFactory.newInstance(asXSD);
		factory.setErrorHandler(null);
		InputStream stream = url.openStream();
		var streamSource = new StreamSource(stream);
		
		/**
		 * Validator fails with even the smallest error
		 * every not compatible version produces an error 
		 * and if the document uses http in the xmlns namespace 
		 * declaration it produces a SAXException
		 * 
		 * TODO: Implement 0.84 version backwards compatible and see
		 * if the error still occures
		 */
		
		Function<String,Boolean> checkValidity = (filePath) -> {
			try {
				var xml = factory.newSchema(new File(filePath));
				xml.newValidator().validate(streamSource);
				return true;
			} catch (SAXException | IOException e) {
				return false;
			}
		};
		
		/**cvc-complex-type.2.4.a: Ungültiger Content 
		wurde beginnend mit Element 
		'{"http://www.sitemaps.org/schemas/sitemap/0.9":lastmod}' 
		 '{"http://www.sitemaps.org/schemas/sitemap/0.9":priority,
		"http://www.sitemaps.org/schemas/sitemap/0.9"]}' wird erwartet.
		*/
		
		var xmlIsASitemap = checkValidity.apply("schemes/sitemap_v9.xsd.xml");
		var xmlIsASiteindex = checkValidity.apply("schemes/siteindex_v9.xsd.xml");
		if(!xmlIsASitemap && ! xmlIsASiteindex) {
			throw new Exception("Sitmap is not valid");
		}
		
		//Parse document
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = instance.newDocumentBuilder();
		builder.setErrorHandler(null);
		Document parsedSitemap = builder.parse(stream);
		
		
		/**
		 * There are 2 possible outcomes - either 
		 * the link is a sitemap or a siteindex in the best
		 * case i can parse just one sitemap or 
		 * in the worst case i have to iterate over the siteindex
		 * and parse every sitemap. in both cases i parse a sitemap
		 */
		Function<String, List<String>> parseSitemap = (link) -> {
			return List.of();
		};
		
		/*
		 * if (sitemap) {
		 * 	return parseSitemap(link);
		 * }
		 * 
		 * List<String> linkList = List.of();
		 * for(sitemap){
		 * 	linkList.add(parseSitemap(link))
		 * }
		 * 
		 * return linkList
		 * 	
		 */
		
		
		
		
		return List.of();
	}
}
