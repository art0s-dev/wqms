package unit.crawler.sitemap;

import static org.junit.jupiter.api.Assertions.*;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static unit.crawler.website.TestSites.*;

import crawler.schemes.loader.StaticSchemeLoader;
import crawler.sitemap.factory.StandardSitemapFactory;

class StandardSitemapFactoryTest {

	static List<URL> pages;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		pages = List.of(new URL("https://www.sap.com/sitemap_index.xml"));
	}

	// TODO: Test malformed sitemaps
	// TODO: Test sitemaps of different standards
	// TODO: Test empty Sitemaps
	// TODO: Test parsed entrys

	// -------------------------------------

	// TODO: When Sitemap is siteindex
	// create List<Sitemap> which has a List<Url>

	@ParameterizedTest
	@MethodSource("getNormalPages")
	@Disabled
	void GivenNormalPages_WhenBuilding_ThenLinkListReturns(URL url) {
		var sitemapFactory = new StandardSitemapFactory();
		{
			var schemes = new StaticSchemeLoader().load();

			sitemapFactory.setUrl(url);
			sitemapFactory.setSchemes(schemes);
		}

		boolean linkListIsPresent = sitemapFactory.build().isPresent();

		assertTrue(linkListIsPresent);
	}

	private static List<URL> getNormalPages() {
		return pages;
	}

}
