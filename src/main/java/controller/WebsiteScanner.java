package controller;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Optional;

public class WebsiteScanner {
	private Optional<Document> document;
	
	public WebsiteScanner(String url) {
		var response = this.scan(url);
		this.document = Optional.empty();
		
		if(!response.isEmpty()) {
			var responseContents = response.orElseThrow();
			var document = this.extractInformations(responseContents);
			this.document = Optional.of(document);
		}
	}
	
	private Optional<HttpResponse<String>> scan(String url){
		try {
			HttpClient client = HttpClient.newBuilder()
					.version(Version.HTTP_1_1)
					.followRedirects(Redirect.NORMAL)
					.connectTimeout(Duration.ofSeconds(10))
					.build();
			
			HttpRequest request = HttpRequest.newBuilder()
					.uri(URI.create(url))
					.build();
			
			return Optional.of(client.send(request, BodyHandlers.ofString()));	
		} catch(Exception e) {
			return Optional.empty();
		}
	}
	
	private Document extractInformations(HttpResponse<String> response) {
		var document = new Document();
		var body = response.body().toString();
		
		document.setBody(body);
		document.setTitle(this.getHtmlContents("title", body));
		return document;
	}
	
	
	private String getHtmlContents(String tag, String body) {
		var startTag = "<" + tag + ">";
		var start = body.indexOf(startTag); 
		var end = body.indexOf("</" + tag + ">");
		
		var contentsWithStartTag = body.substring(start, end);
		var content = contentsWithStartTag.split(startTag);
		return content[content.length - 1];
	}
	

	public Optional<Document> getDocument() { return this.document; }
}
