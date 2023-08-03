import java.net.URL;
import java.util.List;

public class Main {
	public static void main(String[] args) throws Exception {
		URL url = new URL(args[0]);
		var sitemapScanner = new SitemapScanner(url);
		List<String> pageList = sitemapScanner.create();
		pageList.forEach(System.out::println);
	}
}


