package crawler.htmlParser;

import crawler.website.Website;

public final class TagParser implements HtmlParser {

	private Website website;

	public TagParser(Website website) {
		this.website = website;
	}

	public String extract(String information) {
		return searchForTag(information);
	}

	private String searchForTag(String tag) {
		var html = website.body();
		var startTag = "<" + tag + ">";
		int from = html.indexOf(startTag);
		int to = html.indexOf("</" + tag + ">");

		var tagsWereNotFound = checkTagPositions(from, to);
		if (tagsWereNotFound) {
			return "";
		}

		var match = html.substring(from, to);
		return filterStartTag(startTag, match);
	}

	private String filterStartTag(String startTag, String matches) {
		var startTagAndContent = matches.split(startTag);
		return startTagAndContent[startTagAndContent.length - 1];
	}

	private boolean checkTagPositions(int postionOfStartTag, int postionOfEndTag) {
		return postionOfStartTag == -1 || postionOfEndTag == -1;
	}
}
