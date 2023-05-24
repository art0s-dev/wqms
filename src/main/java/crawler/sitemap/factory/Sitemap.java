package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public final class Sitemap {
	public final List<Optional<URL>> linkList;
	
	public Sitemap(List<Optional<URL>> linkList) {
		this.linkList = linkList;
	}
}
