package unit.crawler.extractor;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.extractor.ExtractorWebsiteTestFactory.createDummyWebsites;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import crawler.extractor.Meta;
import crawler.website.Website;
import crawler.extractor.MetaExtractor;

class ExtractorTest {
	static List<Website> websites;
	static String sitesFolder = "src/test/java/sites";
	
	@BeforeAll
	static void beforeAll(){
		websites = createDummyWebsites(sitesFolder);
	}
	
	/**
	 * TODO:Test When Index Substring is not found
	 * TODO: Test When there is no website / Body is empty
	 * TODO: Test when there is malformed html
	 */

	@ParameterizedTest
	@MethodSource("getWebsites")
	void GivenWebsite_WhenExtracting_ThenTitleIsPresent(
		Website website
	) {
		var body = website.body;
		var extractor = new MetaExtractor();
		{
			extractor.setWebsite(website);
		}
		
		Meta meta = extractor.build().orElseThrow();
		Boolean titleIsPresent = !meta.title.isBlank();
	
		assertTrue(titleIsPresent);
	}
	
	private static List<Website> getWebsites(){ return websites; }
	
}
