package integration;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.net.MalformedURLException;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import com.sun.tools.javac.Main;

class MainTest {

	@Test
	void GivenInvalidUrl_WhenMainIsCalled_ThenExceptionIsThrown() {
		var e = assertThrows(MalformedURLException.class, () ->
		Main.main(new String[] {"sadadsdada"}));
		assertTrue(e.getClass() == MalformedURLException.class);
	}
	
	@Test @Disabled
	void GivenUrlToSiteWithoutSitemap_WhenMainIsCalled_ThenExceptionIsThrown() {}
	@Test @Disabled
	void GivenUrlWithOnlyOneSitemapAndItsDamaged_WhenLinkListIsCalled_ThenThrow() {}
	@Test @Disabled
	void GivenLocalSitemap_WhenMainIsCalled_LocalSitemapAndSitesGetParsed() {}
	@Test @Disabled
	void GivenBusinessSitemapWith100Pages_WhenMainIsCalled_ThenExecutionIsUnder6Seconds() {}
}
