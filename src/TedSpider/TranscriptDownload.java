package TedSpider;

import java.util.Iterator;
import java.util.regex.Matcher;

public class TranscriptDownload {
	public static void download(String url, String[] languageType) throws Exception{
		String htmlsc = spider.Crawl.getPage(url);
		Matcher match = TranscriptMatch.regexString(htmlsc, "value='zh-cn'");
		if (!match.find()){
			return;
		}
		TranscriptMatch tsmZh = new TranscriptMatch();
		htmlsc = spider.Crawl.getPage(url+"?language=zh-cn"); 
		tsmZh.match(htmlsc);
		TranscriptMatch tsm = new TranscriptMatch();
		String htmlscl = "";
		for (int i=0; i<languageType.length; i++){
			match = spider.Crawl.regexString(htmlsc, "value='"+languageType[i]+"'");
			if (!match.find()){
				continue;
			}
			htmlscl = spider.Crawl.getPage(url+"?language="+languageType[i]);
			tsm.match(htmlscl);
			tsmZh.addScript(tsm.getScriptList());
		}
		System.out.println(url);
		tsmZh.printResult();
		
		
		
	}
	public static void main(String args[]) throws Exception{
		UrlMatch um = new UrlMatch();
		um.matchPageUrl("https://www.ted.com/talks?language=zh-cn", 2);
		String[] languageType = {"vi", "id", "ms"};
		for (Iterator<String> it = um.urlSet.iterator(); it.hasNext();) {
			System.out.println(it.next());
//			download(it.next(), languageType);
		}
//		download("https://www.ted.com/talks/grady_booch_don_t_fear_superintelligence/transcript", languageType);
	}
}
