package parser;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.beans.StringBean;
import org.htmlparser.util.NodeIterator;
import org.htmlparser.util.NodeList;
import org.htmlparser.visitors.HtmlPage;

public class HTMLParser{
	//格式符处理
	private static final char[] formats={'\r','\f','\t','\b','　'};	
	
	private static String formatReplaceSpace(String str){
		for(char cha:formats) str=str.replace(cha, ' ');
		return str;
	}

	private static String removeTwoSpace(String str){
		while(str.contains(" \n")) str=str.replace(" \n", "\n");
		while(str.contains("\n ")) str=str.replace("\n ", "\n");
		while(str.contains("\n\n")) str=str.replace("\n\n", "\n");
		while(str.contains("  ")) str=str.replace("  ", " ");
		return str;
	}	
	
	private static String removeStartSpace(String str){
		while(str.startsWith(" ")) str=str.replaceFirst(" ", "");
		return str;
	}
	
	private static String removeEndSpace(String str){
		return str.trim();
	}
	
	public static String formatStr(String str){
		str=formatReplaceSpace(str);
		str=removeTwoSpace(str);
		str=removeStartSpace(str);
		str=removeEndSpace(str);
		return str;
	}
	
	//正则表达式解析
	public static String regexParse(String urlstr)throws Exception{
		String page=spider.Crawl.getPage(urlstr);
		//定义script标签的正则表达式
		String scriptRegEx="<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?script[\\s]*?>";
		//定义style标签的正则表达式
		String styleRegEx="<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?\\/[\\s]*?style[\\s]*?>";
		//定义html标签的正则表达式
		String htmlRegEx="<[^>]+>";
		ArrayList<String> regExs=new ArrayList<String>();
		regExs.add(scriptRegEx);
		regExs.add(styleRegEx);
		regExs.add(htmlRegEx);
		
		for(String regEx:regExs){
			Pattern pattern=Pattern.compile(regEx, Pattern.CASE_INSENSITIVE);
			Matcher matcher=pattern.matcher(page);
			page=matcher.replaceAll("");
		}
		return formatStr(page);
	}
	
	//遍历node解析
	static Parser p=new Parser();
	public static String[] nodeParse(String urlstr)throws Exception{
		String page=spider.Crawl.getPage(urlstr);
		String[] fields=new String[2];
		p.setInputHTML(page);
		p.setEncoding(p.getURL());
		HtmlPage hp=new HtmlPage(p);
		p.visitAllNodesWith(hp);
		String title=hp.getTitle();
		NodeList nl=hp.getBody();
		String body="";
		for(NodeIterator ni=nl.elements(); ni.hasMoreNodes();){  
			Node n=ni.nextNode();
			body=body+n.toPlainTextString()+"\n";
		}
		fields[0]=title;
		fields[1]=body;
		return fields;
	}
	
	//抓取解析
	public static String fetchParse(String urlstr)throws Exception{
		StringBean sb=new StringBean();
		sb.setLinks(false);
		sb.setReplaceNonBreakingSpaces(true);
		sb.setCollapse(true);
		sb.setURL(urlstr);
		return sb.getStrings();
	}
	
	public static void main(String[] args) throws Exception{
		String urlstr="http://news.xinhuanet.com/politics/2017-05/08/c_1120934831.htm";
		//System.out.println(regexParse(urlstr));
		
		System.err.println("**********************************************");
		System.out.println("【标题】"+nodeParse(urlstr)[0]);
		System.out.println("【正文】"+nodeParse(urlstr)[1]);
		
		//System.err.println("**********************************************");
		//System.out.println(fetchParse(urlstr));
	}
}