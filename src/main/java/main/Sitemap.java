package main;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

public class Sitemap {
	private static String asXSD = XMLConstants.W3C_XML_SCHEMA_NS_URI;

	public static String[] create(URL url)
			throws ParserConfigurationException, IOException, SAXException, SAXParseException, Exception {

		var rootDocument = parseDocument(url);
		Function<String, Boolean> documentContains = (nodeName) -> {
			return rootDocument.getElementsByTagName(nodeName).getLength() > 0;
		};
		var linkIsSitemap = documentContains.apply("urlset");
		var linkIsSiteindex = documentContains.apply("sitemapindex");

		if (linkIsSitemap) {
			return parseSitemap(rootDocument);
		} else if (linkIsSiteindex) {
			// Link is a siteindex
			throw new Exception("Siteindex not implemented yet");
		} else {
			// TODO: Implement Recursive link search
			throw new Exception("Document has no Valid sitemap");
		}
	}

	public static Document parseDocument(URL url) throws IOException, ParserConfigurationException, SAXException {
		InputStream stream = url.openStream();
		DocumentBuilderFactory instance = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = instance.newDocumentBuilder();
		builder.setErrorHandler(null);
		return builder.parse(stream);
	}
	
	public static String[] parseSitemap(Document document){
		var listOfLocations = document.getElementsByTagName("loc");
		var numberOfNodes = listOfLocations.getLength();
		String[] linkList = {};

		for (int i = 0; i < numberOfNodes; ++i) {
			var node = listOfLocations.item(i).toString();
			linkList[i] = node;
		}

		return linkList;
	}
}
