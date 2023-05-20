package crawler.website;

import java.net.URL;
import java.util.Optional;

public interface WebsiteFactory {
	public void setUrl(URL url);	
	Optional<Website> build();
}
