import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.URL;
import org.junit.jupiter.api.Test;

public class SitemapScannerTest {
	
	@Test
	void GivenUrlToSitemap_WhenLinkListIsCalled_ThenParseToLinkList() throws Exception {
		assertTrue(pageListOfWebpageHasLink(
				"https://www.oracle.com/applications.xml",
				"https://www.oracle.com/applications/applications-unlimited/"
		));
	}

	@Test
	void GivenUrlToSiteindex_WhenLinkListIsCalled_ThenParseToLinkList() throws Exception {
		assertTrue(pageListOfWebpageHasLink(
				"https://www.google.com/sitemap.xml",
				"https://www.google.com/finance/quote/PMVP:NASDAQ"
		));
	}

	@Test
	void GivenUrlToWebspace_WhenLinkListIsCalled_ThenParseToLinkList() throws Exception {
		assertTrue(pageListOfWebpageHasLink(
				"https://www.microsoft.com",
				"https://www.oracle.com/applications/applications-unlimited/"
		));
	}

	private boolean pageListOfWebpageHasLink(String webpage, String link) throws Exception {
		URL url = new URL(webpage);
		var sitemap = new SitemapScanner(url);
		var linkList = sitemap.create();
		return linkList.contains(link);
	}

}