package crawler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Optional;

public interface CanProcessWebPages {
	public default Optional<HttpResponse<String>> scan(String url){
		try {
			HttpClient client = HttpClient.newBuilder()
					.version(Version.HTTP_1_1)
					.followRedirects(Redirect.NORMAL)
					.connectTimeout(Duration.ofSeconds(10))
					.build();
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.build();
			
			var response = client.send(request, BodyHandlers.ofString());	
			return Optional.of(response);	
		} catch(Exception e) {
			return Optional.empty();
		}
	}
}
