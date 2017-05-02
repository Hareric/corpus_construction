package TedSpider;

import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;

public class UrlMatch {
	HashSet<String> urlSet = new HashSet<String>();

	public void match(String htmlSC) {
		Matcher match = spider.Crawl.regexString(htmlSC, "href='(/talks/.*?)(\\?language=.{1,5})?'");
		while (match.find()) {
			this.urlSet.add("https://www.ted.com" + match.group(1) + "/transcript");
		}
	}

	public void matchPageUrl(String url, int startNum, int endNum) throws Exception {

		for (int i = startNum; i <= endNum; i++) {
			System.out.print(url + "?page=" + i);
			try{
				String htmlsc = spider.Crawl.getPage(url + "?page=" + i);
				this.match(htmlsc);
			}
			catch (NullPointerException E){
				continue;
			}
			
			
			System.out.println("  Done!");
		}
	}

	public static void main(String args[]) throws Exception {
		UrlMatch um = new UrlMatch();
		um.matchPageUrl("https://www.ted.com/talks", 5, 40);
		for (Iterator<String> it = um.urlSet.iterator(); it.hasNext();) {
			System.out.println(it.next());
		}
	}
}
