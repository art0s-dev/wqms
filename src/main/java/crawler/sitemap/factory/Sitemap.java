package crawler.sitemap.factory;

import java.net.URL;
import java.util.List;
import java.util.Optional;

public record Sitemap(List<Optional<URL>> linkList) {}
