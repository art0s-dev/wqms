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
		InputStream stream = url.openStream();
		var streamSource = new StreamSource(stream);
		
		Function<String,Boolean> checkValidity = (filePath) -> {
			try {
				var xml = factory.newSchema(new File(filePath));
				xml.newValidator().validate(streamSource);
				return true;
			} catch (SAXException | IOException e) {
				return false;
			}
		};
		
		var xmlIsASitemap = checkValidity.apply("schemes/sitemap.xsd.xml");
		var xmlIsASiteindex = checkValidity.apply("schemes/siteindex.xsd.xml");
		if(!xmlIsASitemap && ! xmlIsASiteindex) {
			throw new Exception("Sitmap is not valid");
		}
		
		//Parse document
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = instance.newDocumentBuilder();
		builder.setErrorHandler(null);
		Document parsedSitemap = builder.parse(stream);
		
		return List.of();
	}
}
