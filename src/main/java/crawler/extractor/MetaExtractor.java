package crawler.extractor;

import java.util.Optional;

import crawler.website.Website;

public final class MetaExtractor implements 
CanAcceptWebsite, CanBuildMeta {
	
	Website website;

	public Optional<Meta> build() {
		
		var website = this.website;
		var websiteWasNotSet = website == null;
		
		if(websiteWasNotSet) {
			return Optional.empty();
		}
		
		var title = this.searchForTag("title");
		
		var meta = new Meta(title);
		return Optional.of(meta);
	}

	public void setWebsite(Website website) {
		this.website = website;
	}
	
	private String searchForTag(String tag) {
		var html = this.website.body;
		
		var startTag = "<" + tag + ">";
		int from = html.indexOf(startTag); 
		int to = html.indexOf("</" + tag + ">");
		
		var tagsWereNotFound = checkTagPositions(from, to);
		if(tagsWereNotFound) {
			return "";
		}
		
		var matches = html.substring(from,to);
		return filterStartTag(startTag, matches);
	}

	private String filterStartTag(String startTag, String matches) {
		var startTagAndContent = matches.split(startTag);
		return startTagAndContent[startTagAndContent.length - 1];
	}

	private boolean checkTagPositions(int postionOfStartTag, int postionOfEndTag) {
		return postionOfStartTag == -1 
		|| postionOfEndTag == -1;
	}

}
