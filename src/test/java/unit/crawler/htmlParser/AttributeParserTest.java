package unit.crawler.htmlParser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import crawler.htmlParser.AttributeParser;
import crawler.website.Website;

class AttributeParserTest {

    String markup = "<meta someOther=\"true\"/>" + "<meta name=\"description\" content=\"testValue\"/>";

    String keywordMarkup = "<meta name=\"keywords\" content=\"Baden-Württemberg, "
	    + "Landesregierung, Landesportal, Ministerpräsident" + ",Landeskunde, Politik,\" />";

    @Test
    void GivenWebpage_WhenExtracting_ThenReturnContentsOfAttribute() {
		var website = new Website(markup);
		var attributeParser = new AttributeParser(website);
		var text = attributeParser.extract("description");
	
		assertTrue(text.contentEquals("testValue"));
    }

    @Test
    void GivenWebpage_WhenExtracting_ThenReturnKeywordList() {
		var website = new Website(keywordMarkup);
		var attributeParser = new AttributeParser(website);
		List<String> keywords = attributeParser.extractKeywords();
		var keywordsAreComplete = checkKeywords(keywords);
	
		assertTrue(keywordsAreComplete);
    }

    private boolean checkKeywords(List<String> keywords) {
    	return keywords.contains("Baden-Württemberg") 
    			&& keywords.contains("Ministerpräsident");
    }
}
