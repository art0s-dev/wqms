package crawler.website;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import static java.util.Optional.empty;

import javax.xml.transform.stream.StreamSource;

public final class StandardWebsiteFactory implements WebsiteFactory {
	private URL url;

	public Optional<Website> build() {
		var urlWasNotSet = url == null;

		if (urlWasNotSet) {
			return empty();
		}

		try (var stream = url.openStream()) {
			var source = new StreamSource(stream);
			var body = source.toString();
			var website = new Website(body);

			return Optional.of(website);
		}

		catch (IOException e) {
			return empty();
		}
	}

	public void setUrl(URL url) {
		this.url = url;
	}
}
