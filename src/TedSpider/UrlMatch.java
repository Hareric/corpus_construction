package TedSpider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;

public class UrlMatch {
	HashSet<String> urlSet = new HashSet<String>();

	public void match(String htmlSC) {
		Matcher match = spider.Crawl.regexString(htmlSC, "href='(/talks/.*?)'(>|\\?language=zh-cn)");
		while (match.find()) {
			this.urlSet.add("https://www.ted.com" + match.group(1) + "/transcript");
		}
	}

	public void matchPageUrl(String url, int pageNum) throws Exception {

		for (int i = 1; i <= pageNum; i++) {
			System.out.print(url + "?page=" + i);
			String htmlsc = spider.Crawl.getPage(url + "?page=" + i);
			this.match(htmlsc);
			System.out.println("  Done!");
		}
	}

	public static void main(String args[]) throws Exception {
		UrlMatch um = new UrlMatch();
		um.matchPageUrl("https://www.ted.com/talks", 5);
		for (Iterator<String> it = um.urlSet.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}
