package unit.crawler.website;

import static org.junit.jupiter.api.Assertions.*;
import static unit.crawler.website.TestSites.*;

import java.net.URL;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import crawler.website.Website;
import crawler.website.StandardWebsiteFactory;

class StandardWebsiteFactoryTest {

	@ParameterizedTest
	@MethodSource("getNormalPages")
	void GivenUrl_WhenFactoryBuilds_WebsiteHasContent(URL url){
		var factory = new StandardWebsiteFactory();
		{
			factory.setUrl(url);
		}

		Website website = factory.build().orElseThrow();
		var websiteHasContent = !website.body().isBlank();
		
		assertTrue(websiteHasContent);
	}
	
	@Test
	void GivenUrl_WhenUrlIsNotSet_ThenWebsiteIsEmpty() {
		var factory = new StandardWebsiteFactory();
		var website = factory.build();
		
		assertTrue(website.isEmpty());
	}
	
	static List<URL> getNormalPages(){ 
		return getUrlsFrom(normalSitemaps); 
	}
}
