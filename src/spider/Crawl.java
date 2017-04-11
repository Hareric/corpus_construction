package spider;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawl {
	public static void append(String file, String str, String code) throws Exception {
		RandomAccessFile raf = new RandomAccessFile(file, "rw");

		raf.seek(raf.length());
		raf.write(str.getBytes(code));
		raf.close();
		System.out.println(file + "      写入完成");
	}

	public static String getPage(String url) throws Exception {
		String result = null;
		HttpURLConnection huc = (HttpURLConnection) new URL(url).openConnection();
		huc.setRequestMethod("GET");
		huc.setUseCaches(true);
		huc.connect();
		if (huc.getResponseCode() == HttpURLConnection.HTTP_OK) {
			BufferedReader bf = new BufferedReader(new InputStreamReader(huc.getInputStream(), "UTF-8"));
			StringBuffer sb = new StringBuffer();
			String line = null;
			while ((line = bf.readLine()) != null) {
				sb.append(line + "\n");
			}
			result = sb.toString();
			bf.close();
		} else if (huc.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
			System.err.println("HTTP_NOT_FOUND");
		}
		return result;
	}

	public static Matcher regexString(String targetStr, String patternStr) {
		// 定义一个样式模板，此中使用正则表达式，括号中是要抓的内容
		Pattern pattern = Pattern.compile(patternStr, Pattern.DOTALL);
		// 定义一个matcher用来做匹配
		Matcher matcher = pattern.matcher(targetStr);
		return matcher;
	}
	//
	// public static void newsMathInfo(String newsUrl)throws Exception{
	// String homePage = getPage(newsUrl);
	// Matcher titleMatcher = regexString(homePage,
	// "<.*?id=\".*?itle\".*?>(.*?)</h1>");
	// Matcher timeMatcher = regexString(homePage,
	// "<span.*?id=\"navtimeSource\">(.*?)<span>");
	// Matcher timeMatcher2 = regexString(homePage, "<span
	// class=\"titer\">(.*?)</span>");
	// Matcher timeMatcher3 = regexString(homePage, "<span
	// id=\"pub_date\">(.*?)</span>");
	// Matcher contentLineMatcher = regexString(homePage, "<p.*?>(.*?)</p>");
	// String news = "";
	// String title = "";
	// if (titleMatcher.find()){
	// title = titleMatcher.group(1);
	// news += "标题：" + title + "\r\n";
	// }
	// if (timeMatcher.find()){
	// news += "时间：" + timeMatcher.group(1) + "\r\n";
	// }
	// else if (timeMatcher2.find()){
	// news += "时间：" + timeMatcher2.group(1) + "\r\n";
	// }
	// else if (timeMatcher3.find()){
	// news += "时间：" + timeMatcher3.group(1) + "\r\n";
	// }
	// String line;
	// news += "正文：\r\n";
	// while (contentLineMatcher.find()){
	// line = contentLineMatcher.group(1).replaceAll("<.*?>",
	// "").replaceAll("&nbsp", "")+"\r\n";
	// if(line.contains("新浪简介")){
	// break;
	// }
	// news += " " + line;
	// }
	// title = title.replaceAll("[\\pP\\p{Punct}]", "");
	// append("pages/"+title+".txt", news, "UTF-8");
	// }
	//
	// public static void main(String[] args)throws Exception{
	// String urlSave = ""; // 保存该网页匹配的网页链接地址
	// String homePage = getPage("http://ent.sina.com.cn/");
	// Matcher urlMatcher = regexString(homePage,
	// "href=\"(http[s]?://ent.sina.com.cn/s/m/\\d{4}-\\d{2}-\\d{2}.{0,30}shtml)\"");
	// String newsURL;
	// while (urlMatcher.find()){
	// newsURL = urlMatcher.group(1);
	// newsMathInfo(newsURL);
	// urlSave += newsURL+"\r\n";
	// }
	// append("pages/urls.txt", urlSave, "UTF-8");
	//
	//
	// }
}