package unit.crawler.sitemap.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;

import crawler.sitemap.parser.StandardSeoMapParser;

import static unit.crawler.website.TestSites.localGovernmentSitemap;

class StandardSeoMapParserTest {

	@Test
	void GivenNoUrl_WhenParserIsCalled_ThenNoDocumentIsReturned() {
		var parser = new StandardSeoMapParser();
		Optional<Document> document = parser.parse();
		
		assertTrue(document.isEmpty());
	}

	@Test 
	void GivenUrlWithNoDocument_WhenParserIsCalled_ThenNoDocumentIsReturned() throws MalformedURLException {
		var url = new URL("https://www.sap.com");
		var parser = new StandardSeoMapParser();
		{
			parser.setUrl(url);
		}
		
		Optional<Document> document = parser.parse();
		
		assertTrue(document.isEmpty());
	}
	
	
	@Test
	void GivenValidDocument_WhenParserIsCalled_ThenDocumentIsReturned() throws MalformedURLException {
		var url = new URL(localGovernmentSitemap);
		var parser = new StandardSeoMapParser();
		{
			parser.setUrl(url);
		}
		
		var document = parser.parse().orElseThrow();
		assertTrue(document instanceof Document);
	}

}
