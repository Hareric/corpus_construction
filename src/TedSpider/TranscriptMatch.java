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
				String t = sentMatcher.group(1).replaceAll("[\\t\\n\\r]", "");
				t = t.replaceAll("&quot;", "\"").replaceAll("&amp;", "&").replaceAll("&lt;", "<").replaceAll("&gt;", ">").replaceAll("nbsp;", " ");	
				s += t;
			}
			this.scriptList.add(s);
		}

	}

	public boolean isAbeforeB(String timeA, String timeB) {
//		System.out.println(timeA.split(":")[1] + "    " + timeB.split(":")[1]);
		if (Integer.parseInt(timeA.split(":")[0].trim()) < Integer.parseInt(timeB.split(":")[0].trim())) {
			return true;
		} else if (Integer.parseInt(timeA.split(":")[0].trim()) > Integer.parseInt(timeB.split(":")[0].trim())) {
			return false;
		} else if (Integer.parseInt(timeA.split(":")[1].trim()) < Integer.parseInt(timeB.split(":")[1].trim())) {
			return true;
		}
		return false;
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

	/**
	 * 在没一句前加入语言类型
	 * @param type
	 */
	public void addLanguageType(String type){
		for (int i = 0; i < this.scriptList.size(); i++) {
			this.scriptList.set(i, type + "####   " + scriptList.get(i));
		}
	}
	/**
	 * 将2份不同语言的脚本合二维一
	 * @param newScriptList
	 * @param newTimeList
	 * @throws Exception
	 */
	public void addScript(ArrayList<String> newScriptList, ArrayList<String> newTimeList) throws Exception {
		if (newScriptList.size() == this.scriptList.size()){
			for (int i = 0; i < this.scriptList.size(); i++) {
				this.scriptList.set(i, scriptList.get(i) + "\n" + newScriptList.get(i));
			}
			return;
		}
		else{
			System.err.print("时间轴无法对应无法添加");
			throw new Exception();
		}
//		else{
//			for (int i = 0, j=0; i < this.scriptList.size(); i++, j++) {
//				if(newTimeList.get(j).equals(this.timeList.get(i))){
//					if(this.scriptList.get(i).endsWith("TobeContinued")){
//						String mergeString = this.scriptList.get(i);
//						mergeString = mergeString.substring(0, mergeString.length()-13);
//						mergeString += newScriptList.get(j);
//						this.scriptList.set(i, mergeString);
//					}
//					else{
//						this.scriptList.set(i, scriptList.get(i) + "\n" + newScriptList.get(j));
//					}
//					
//				}
//				
//				else{
//					if(this.isAbeforeB(newTimeList.get(j), this.timeList.get(i))){
//						this.scriptList.set(i, scriptList.get(i) + "\n" + newScriptList.get(j) + "TobeContinued");
//						i--;
//					}
//					else{
//						this.scriptList.set(i, scriptList.get(i) + "(接下节)\n" + newScriptList.get(j));
//						this.scriptList.set(i+1, "(接上节)" + scriptList.get(i+1));
//						i++;
//					}
//				}
//			}
//		}
	}

	public static void main(String[] args) throws Exception {
		String sc = spider.Crawl.getPage(
				"https://www.ted.com/talks/alan_smith_why_we_re_so_bad_at_statistics/transcript?language=zh-tw");
		TranscriptMatch tm = new TranscriptMatch();
		tm.match(sc);
		tm.printResult();
	}
}
