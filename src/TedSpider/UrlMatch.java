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

	/**
	 * 爬取指定页码的演讲稿链接
	 * @param url
	 * @param startNum
	 * @param endNum
	 * @throws Exception
	 */
	public void matchPageUrl(String url, int startNum, int endNum) throws Exception {

		for (int i = startNum; i <= endNum; i++) {
			System.out.print(url + "&page=" + i);
			try{
				String htmlsc = spider.Crawl.getPage(url + "&page=" + i);
				this.match(htmlsc);
			}
			catch (NullPointerException E){
				System.err.println(" faile");
				i--;
				Thread.sleep(3000);
				continue;
			}
			
			
			System.out.println("  Done!");
		}
	}
	
	/**
	 * 爬取所有演讲稿的链接
	 * @param url
	 * @throws Exception
	 */
	public void matchPageUrl(String url) throws Exception {
		int len = -1;  // 记录网页的长度 若连续两页的长度相同 则表明页数已到底
		for (int i = 1; i <= 200; i++) {
			System.out.print(url + "&page=" + i);
			try{
				String htmlsc = spider.Crawl.getPage(url + "&page=" + i);
				if (htmlsc.length() == len && htmlsc.length()>40000 && htmlsc.length()<42000){
					System.out.println("  break!");
					break;
				}else{
					len = htmlsc.length();
				}
				this.match(htmlsc);
			}
			catch (NullPointerException E){
				System.err.println(" faile");
				i--;
				Thread.sleep(3000);
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
