package crawler.website;

import java.net.URL;
import java.util.Optional;

public interface WebsiteFactory {
	Optional<Website> build();
	public void setUrl(URL url);
}
