package crawler;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpClient.Redirect;
import java.net.http.HttpClient.Version;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class WebsiteScanner {
	private Optional<Document> document;
	
	public WebsiteScanner(String url) {
		Optional<HttpResponse<String>> response = this.scan(url);
		this.document = Optional.empty();
		
		if(!response.isEmpty()) {
			var responseContents = response.orElseThrow();
			Document document = this.extractInformations(responseContents);
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
			
			var response = client.send(request, BodyHandlers.ofString());	
			return Optional.of(response);	
		} catch(Exception e) {
			return Optional.empty();
		}
	}
	
	private Document extractInformations(HttpResponse<String> response) {
		var document = new Document();
		var body = response.body().toString();
		
		document.setBody(body);
		document.setTitle(this.searchForTag("title", body));
		document.setDescription(this.searchForAttribute("meta", "description", body));
		
		String keywords = this.searchForAttribute("meta", "keywords", body);
		document.setKeywords(this.createKeywordList(keywords));
		return document;
	}
	
	private String searchForTag(String tag, String html) {
		var startTag = "<" + tag + ">";
		int postionOfStartTag = html.indexOf(startTag); 
		int postionOfEndTag = html.indexOf("</" + tag + ">");
		
		String foundString = html.substring(postionOfStartTag, postionOfEndTag);
		String[] stringList = foundString.split(startTag);
		String contentsRightFromStartTag = stringList[stringList.length - 1];
		return contentsRightFromStartTag;
	}
	
	private String searchForAttribute(String tag, String attribute, String html) {
		int postitionOfAttribute = html.indexOf(attribute);
		boolean attributeDoesNotExist = postitionOfAttribute == -1;
		if(attributeDoesNotExist) {
			return "";
		}
		
		String foundString = html.substring(postitionOfAttribute);
		String[] leftAndRightFromDeclaration = foundString.split("content=", 2);
		String contentsRight = leftAndRightFromDeclaration[leftAndRightFromDeclaration.length - 1];
		String[] leftAndRightAfterClosingTag = contentsRight.split(">");
		String contentsLeft = leftAndRightAfterClosingTag[0];
		
		var builder = new StringBuilder();
		var doubleQuotes = builder.append('"').toString();
		var valueOfAttribute = contentsLeft.replace(doubleQuotes,"");
		return valueOfAttribute;
	}
	
	private List<String> createKeywordList(String keywordString){
		if(keywordString.isBlank()) {
			return Arrays.asList();
		}
		
		var list = Arrays.asList(keywordString.split(","));
		return list.parallelStream()
		.map(keyword -> keyword.strip())
		.collect(Collectors.toList());
	}
		

	public Optional<Document> getDocument() { return this.document; }
}
