import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

class LinkListTest {
	@Test @Disabled
	void GivenUrlWithNoSitemap_WhenLinkListIsCalled_ThenThrow() {}
	@Test @Disabled
	void GivenUrlWithOnlyOneSitemapAndItsDamaged_WhenLinkListIsCalled_ThenThrow() {}
	@Test @Disabled
	void GivenUrlWithMoreThanOneSitemapsAndOneIsDamaged_ThenLinkListIsCalled_ThenOmit() {}
	@Test @Disabled
	void GivenUrlToLocalSiteindex_WhenLinkListIsCalled_ThenParseToLinkList() {}
	@Test @Disabled
	void GivenUrlToLocalSitemap_WhenLinkListIsCalled_ThenParseToLinkList() {}
}
