package crawler.extractor;

import java.util.Optional;

import crawler.website.Website;

public final class StandardMetaExtractor implements MetaExtractor {
	
	Website website;
	String markupBeforeValue = "content=";
	
	public Optional<Meta> build() {
		
		var website = this.website;
		var websiteWasNotSet = website == null;
		
		if(websiteWasNotSet) {
			return Optional.empty();
		}
		
		var title = this.searchForTag("title");
		var description = this.searchForAttribute("description");
		var meta = new Meta(title,description);
		
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
		
		var match = html.substring(from,to);
		return filterStartTag(startTag, match);
	}
	
	private String filterStartTag(String startTag, String matches) {
		var startTagAndContent = matches.split(startTag);
		return getLast(startTagAndContent);
	}

	private boolean checkTagPositions(int postionOfStartTag, int postionOfEndTag) {
		return postionOfStartTag == -1 
		|| postionOfEndTag == -1;
	}
	
	private String searchForAttribute(String attribute) {
		var html = this.website.body;
		int from = html.indexOf(attribute);
		
		var attributeDoesNotExist = from == -1;
		if(attributeDoesNotExist) {
			return "";
		}
		
		var match = html.substring(from);
		
		var tagAndValue = match.split(markupBeforeValue);
		var valueWithClosingTag = getLast(tagAndValue);
		var valueAndClosingTag = valueWithClosingTag.split(">");
		var value =  valueAndClosingTag[0];
		
		return replaceDoubleQuotes(value);
	}
	
	private String getLast(String[] array) {
		return array[array.length - 1];
	}
	
	private String replaceDoubleQuotes(String string) {
		var doubleQuotes = new StringBuilder()
				.append('"')
				.toString();
		
		return string.replace(doubleQuotes,"");
	}

}
