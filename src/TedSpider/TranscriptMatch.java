package TedSpider;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TranscriptMatch {
	private ArrayList<String> scriptList;
	private ArrayList<String> timeList;

	public TranscriptMatch() {
		scriptList = new ArrayList<String>();
		timeList = new ArrayList<String>();
	}

	public static Matcher regexString(String targetStr, String patternStr) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		return matcher;
	}

	public void match(String htmlSC) {
		ArrayList<String> paraList = new ArrayList<String>();
		Matcher paraMatcher = regexString(htmlSC, "<p class='talk-transcript__para'>(.*?)</p>");
		while (paraMatcher.find()) {
			paraList.add(paraMatcher.group(1));
		}

		for (String para : paraList) {
			Matcher timeMatcher = regexString(para, "<data class='talk-transcript__para__time'>(.*?)</data>");
			Matcher sentMatcher = regexString(para,
					"<span class='talk-transcript.*?' data-time='\\d*?' id='t-\\d*?'>(.*?)</span>");
			if (timeMatcher.find()) {
				this.timeList.add(timeMatcher.group(1).replaceAll("[\\t\\n\\r]", ""));
			}
			String s = "";
			while (sentMatcher.find()) {
				s += sentMatcher.group(1).replaceAll("[\\t\\n\\r]", "");
			}
			this.scriptList.add(s);
		}

	}

	public ArrayList<String> getTimeList() {
		return this.timeList;
	}

	public ArrayList<String> getScriptList() {
		return this.scriptList;
	}

	public void printResult() {
		for (int i = 0; i < this.scriptList.size(); i++) {
			System.out.println(this.timeList.get(i));
			System.out.println(this.scriptList.get(i));
		}
	}
	
	public void addScript(ArrayList<String> newScriptList){
		for(int i = 0; i < this.scriptList.size(); i++)
		{
			this.scriptList.set(i, scriptList.get(i)+"\n"+newScriptList.get(i));
		}
	}

	public static void main(String[] args) throws Exception {
		String sc = spider.Crawl.getPage(
				"https://www.ted.com/talks/alan_smith_why_we_re_so_bad_at_statistics/transcript?language=zh-tw");
		TranscriptMatch tm = new TranscriptMatch();
		tm.match(sc);
		tm.printResult();
	}
}
