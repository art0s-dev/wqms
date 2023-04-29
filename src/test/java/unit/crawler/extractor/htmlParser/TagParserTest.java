package unit.crawler.extractor.htmlParser;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import crawler.website.Website;
import crawler.htmlParser.TagParser;

class TagParserTest {

	@Test
	void GivenWebpage_WhenExtracting_ThenReturnContentsOfTag() {
		var html = ".../><title>Website</title><...";
		var website = new Website(html);
		var tagParser = new TagParser(website);
		var text = tagParser.extract("title");
		
		assertTrue(text.contentEquals("Website"));
	}

}
