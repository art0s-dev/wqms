package unit;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import main.Sitemap;

class SitemapTest {
	
	@Test @Disabled
	void GivenUrlWithNoSitemap_WhenLinkListIsCalled_ThenThrow() 
	throws MalformedURLException {
		URL url = new URL("https://www.microsoft.com");
		var e = assertThrows(Exception.class, () -> Sitemap.create(url) );
		assertTrue(e.getClass() == Exception.class);
	}
	
	@Test
	void GivenUrlToLocalSitemap_WhenLinkListIsCalled_ThenParseToLinkList()
	throws SAXParseException, ParserConfigurationException, IOException, SAXException, Exception {
		URL url = new URL("https://www.oracle.com/applications.xml");
		List<String> sitemap = Sitemap.create(url);
		assertTrue(sitemap.get(2) == "https://www.oracle.com/applications/applications-unlimited/");
	}
}

