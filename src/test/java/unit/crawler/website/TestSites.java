package unit.crawler.website;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public final class TestSites {
	public static List<String> normalSitemaps = Arrays.asList(
		"https://www.baden-wuerttemberg.de/",
		"http://www.wikipedia.org/"
	);

	public static List<String> noRobots = Arrays.asList(
		"http://chat.openai.de/"
	);
	
	public static List<String> unusualSitemaps = Arrays.asList(
		"https://www.lufthansa.com/",
		"https://sap.com/"
	);
	
	public static String localGovernmentSitemap = "https://www.baden-wuerttemberg.de/sitemap.xml";
	
	public static List<URL> getUrlsFrom(List<String> linkList)  {
		return linkList.parallelStream()
				.map(link -> toUrl(link))
				.filter(url -> url.isPresent())
				.map(url -> url.orElseThrow())
				.collect(Collectors.toList());
	}
	
	private static Optional<URL> toUrl(String link) {
		try { 
			var url = new URL(link);
			return Optional.of(url); 
		} catch ( MalformedURLException e ) { 
			return Optional.empty(); 
		}
	}
}