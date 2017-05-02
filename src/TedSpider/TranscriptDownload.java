package TedSpider;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;

public class TranscriptDownload {
	public static void download(String url, String[] languageType) throws Exception{
		String htmlsc = spider.Crawl.getPage(url);
		Matcher match = TranscriptMatch.regexString(htmlsc, "value='zh-cn'");
		if (!match.find()){  // 若无中文翻译文本则返回不爬取
			return;
		}
		TranscriptMatch tsmZh = new TranscriptMatch();
		htmlsc = spider.Crawl.getPage(url+"?language=zh-cn"); 
		tsmZh.match(htmlsc);
		String htmlscl = "";
		for (int i=0; i<languageType.length; i++){
			TranscriptMatch tsm = new TranscriptMatch();

			match = spider.Crawl.regexString(htmlsc, "value='"+languageType[i]+"'");
			if (!match.find()){
				continue;
			}
			htmlscl = spider.Crawl.getPage(url+"?language="+languageType[i]);
			tsm.match(htmlscl);
//			tsm.printResult();
//			tsmZh.printResult();
//			System.out.println(tsm.getScriptList().size());
//			System.out.println(tsmZh.getScriptList().size());
			try{
				tsmZh.addScript(tsm.getScriptList(), tsm.getTimeList());}
			catch (Exception e){
				continue;
			}
		}
		tsmZh.printResult();
		Matcher titleMatch = TranscriptMatch.regexString(url, "talks/(.*?)/transcript");
		String title = "";
		if (titleMatch.find()){
			title = titleMatch.group(1);
		}
		
		writeFile("transcript/"+title+".txt", tsmZh.getScriptList(), tsmZh.getTimeList(), "UTF-8");
	}
	
	public static void writeFile(String fileName, ArrayList<String> scriptList, ArrayList<String> timeList, String code) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		raf.seek(raf.length());
		for (int i = 0; i < scriptList.size(); i++){
			raf.write(timeList.get(i).getBytes(code));
			raf.write("\n".getBytes());
			raf.write(scriptList.get(i).getBytes(code));
			raf.write("\n".getBytes(code));
		}
		raf.close();
		System.out.println(fileName + "      写入完成");
	}
	
	public static void main(String args[]) throws Exception{
		UrlMatch um = new UrlMatch();
		um.matchPageUrl("https://www.ted.com/talks?language=zh-cn", 51, 60);
		String[] languageType = {"vi", "id", "ms"};
		for (Iterator<String> it = um.urlSet.iterator(); it.hasNext();) {
			System.out.println(it.next());
			try{
				download(it.next(), languageType);
			}
			catch (NullPointerException ex){
				continue;
			}
			
		}
//		String[] languageType = {"vi", "id", "ms"};
//		download("https://www.ted.com/talks/joe_lassiter_we_need_nuclear_power_to_solve_climate_change/transcript", languageType);
//		download("https://www.ted.com/talks/adam_galinsky_how_to_speak_up_for_yourself/transcript", languageType);
//		download("https://www.ted.com/talks/alaa_murabit_what_my_religion_really_says_about_women/transcript", languageType);
	}
}
