package spider;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
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

	public static String getPage(String url) throws Exception{
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
			throw new Exception("HTTP_NOT_FOUND");
//			System.err.println("HTTP_NOT_FOUND");
		}
		return result;
		
		
//		StringBuffer html = new StringBuffer();  
//        String result = null;  
//        try{  
//            URL url = new URL(address);  
//            URLConnection conn = url.openConnection();
//            String ug[] = {"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 V1_AND_SQ_5.0.0_146_YYB_D QQ/5.0.0.2215",
//            		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.3 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/535.19 (KHTML, like Gecko) Version/1.0 Chrome/18.0.1025.308 Mobile Safari/535.19",
//            		"Mozilla/5.0 (Linux; U; Android 4.3; zh-CN; SM-N9009 Build/JSS15J) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-CN; Coolpad 5891 Build/JZO54K) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.3.478 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; U; Android 4.1.2; zh-cn; Coolpad 5891 Build/JZO54K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 V1_AND_SQ_5.0.0_146_YYB2_D QQ/5.0.0.2215",
//            		"Mozilla/5.0 (iPhone; CPU iPhone OS 7_0_4 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11B554a Safari/9537.53",
//            		"Mozilla/5.0 (iPhone; CPU iPhone OS 7_1_2 like Mac OS X) AppleWebKit/537.51.2 (KHTML, like Gecko) Mobile/11D257 QQ/5.0.0.165",
//            		"Mozilla/5.0 (Linux; Android 4.3; zh-cn; SAMSUNG-GT-I9308_TD/1.0 Android/4.3 Release/11.15.2013 Browser/AppleWebKit534.30 Build/JSS15J) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30 SogouMSE,SogouMobileBrowser/3.2.3",
//            		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/5.0.3.10 (Baidu; P1 4.2.2)",
//            		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-cn; SCH-I959 Build/JDQ39) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0",
//            		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36 OPR/24.0.1565.82529",
//            		"Mozilla/5.0 (Linux; U; Android 4.4.4; zh-CN; Nexus 4 Build/KTU84P) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/5.2.3.0 (Baidu; P1 4.0.4)",
//            		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/537.36 (KHTML, like Gecko)Version/4.0 MQQBrowser/5.3 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; Android 4.0.4; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/535.19 (KHTML, like Gecko) Version/4.0 LieBaoFast/2.12.0 Mobile Safari/535.19",
//            		"Opera/9.80 (Android; Opera Mini/7.0.31907/34.2499; U; zh) Presto/2.8.119 Version/11.10",
//            		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HW-HUAWEI_C8825D/C8825DV100R001C92B943SP01; 480*800; CTC/2.0) AppleWebKit/534.30 Mobile Safari/534.30",
//            		"Mozilla/5.0 (Linux; U; Android 4.4.2; zh-CN; SGP521 Build/17.1.2.A.0.314) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; Android 4.4.2; SGP521 Build/17.1.2.A.0.314) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Safari/537.36",
//            		"Mozilla/5.0 (Linux; U; Android 4.0.4; zh-CN; HUAWEI C8825D Build/HuaweiC8825D) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.8.5.442 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; GT-N7100 Build/JRO03C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Mozilla/5.0 (Linux; Android 4.4.2; zh-cn; SAMSUNG-SM-N9009 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Version/1.5 Chrome/28.0.1500.94 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; U; Android 4.2.2; zh-CN; HTC HTL22 Build/JDQ39) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/37.0.2062.117 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; U; Android 4.2.1; zh-cn; AMOI A920W Build/JOP40D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Mozilla/5.0 (Linux; Android 4.3; SM-N9009 Build/JSS15J) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/36.0.1985.135 Mobile Safari/537.36",
//            		"Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; GT-N7100 Build/JRO03C) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.9.2.467 U3/0.8.0 Mobile Safari/533.1",
//            		"Mozilla/5.0 (Linux; U; Android 4.3; zh-cn; R8007 Build/JLS36C) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Huawei U8800    Android 2.3.3   Baidu 2.2   Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.2 Version/4.0 Mobile Safari/530.17",
//            		"Huawei U8800    Android 2.3.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31",
//            		"Meizu MX M031   Android 4.0.3   Chrome 18   Mozilla/5.0 (Linux; Android 4.0.3; M031 Build/IML74K) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19",
//            		"Meizu MX M031   Android 4.0.3   Opera 12.1  Opera/9.80 (Android 4.0.3; Linux; Opera Mobi/ADR-1210241511) Presto/2.11.355 Version/12.10",
//            		"Meizu MX M031   Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Meizu MX M031   Android 4.0.3   Baidu 2.2   Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.2 Version/4.0 Mobile Safari/530.17",
//            		"Meizu MX M031   Android 4.0.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31",
//            		"Meizu M9    Android 4.0.3   QQ 3.7  MQQBrowser/3.7/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30    Normal Mode",
//            		"Meizu M9    Android 4.0.3   QQ 3.5  MQQBrowser/3.5/Adr (Linux; U; 4.0.3; zh-cn; M9 Build/Flyme 1.0.1;640*960)   Speed Mode",
//            		"Meizu M9    Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Meizu M9    Android 4.0.3   QQ 3.5  MQQBrowser/3.5/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M9 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30    Normal Mode",
//            		"Meizu MX M031   Android 4.0.3   Maxthon 2.7     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Huawei U8800    Android 2.3.3   Maxthon 2.7     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            		"Huawei U8800    Android 2.3.3   QQ 3.7  MQQBrowser/3.7/Adr (Linux; U; 2.3.5; zh-cn; U8800 Build/U8800V100R001C00B528G002;480*800)   Speed Mode",
//            		"Huawei U8800    Android 2.3.3   Dolphin 9.1     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            		"Huawei U8800    Android 2.3.3   QQ 3.7  MQQBrowser/3.7/Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1  Normal Mode",
//            		"Huawei U8800    Android 2.3.3   -built-in *     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            		"Samsung P6200(GALAXY Tab)   Android 3.2     -built-in *     Mozilla/5.0 (Linux; U; Android 3.2; zh-cn; GT-P6200 Build/HTJ85B) AppleWebKit/534.13 (KHTML, like Gecko) Version/4.0 Safari/534.13",
//            		"Huawei U8800    Android 2.3.3   Maxthon 4.0     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            		"Meizu MX M031   Android 4.0.3   Baidu 2.3   Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.3 Version/4.0 Mobile Safari/530.17 baidubrowser/023_1.41.3.2_diordna_069_046/uzieM_51_3.0.4_130M/1200a/963E77C7DAC3FA587DF3A7798517939D%7C408994110686468/1",
//            		"Huawei U8800    Android 2.3.3   Baidu 2.3   Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/530.17 (KHTML, like Gecko) FlyFlow/2.3 Version/4.0 Mobile Safari/530.17 baidubrowser/042_1.6.3.2_diordna_008_084/IEWAUH_01_5.3.2_0088U/1001a/BE44DF7FABA8768B2A1B1E93C4BAD478%7C898293140340353/1",
//            		"Huawei U8800    Android 2.3.3   Dolphin 9.2     Mozilla/5.0 (Linux; U; Android 2.3.5; zh-cn; U8800 Build/HuaweiU8800) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 Mobile Safari/533.1",
//            		"HTC S720e(One X)    Android 4.0.3   -built-in *     Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HTC S720e Build/IMM76D) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"HTC S720e(One X)    Android 4.0.3   UC 8.7  Mozilla/5.0 (Linux; U; Android 4.0.4; zh-cn; HTC S720e Build/IMM76D) UC AppleWebKit/534.31 (KHTML, like Gecko) Mobile Safari/534.31",
//            		"Meizu MX M031   Android 4.0.3   Dolphin Min 2.3     Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Meizu MX M031   Android 4.0.3   QQ 4.0  MQQBrowser/4.0/Mozilla/5.0 (Linux; U; Android 4.0.3; zh-cn; M031 Build/IML74K) AppleWebKit/533.1 (KHTML, like Gecko) Mobile Safari/533.1",
//            		"Meizu M9    Android 4.0.3   QQ 3.7  MQQBrowser/3.7/Adr (Linux; U; 4.0.3; zh-cn; M9 Build/Flyme 1.0.1;640*960)",
//            		"Meizu MX2 M040  Android 4.1     UC 9.4  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; M040 Build/JRO03H) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 UCBrowser/9.4.1.362 U3/0.8.0 Mobile Safari/533.1",
//            		"Meizu MX2 M040  Android 4.1     Chrome 31   Mozilla/5.0 (Linux; Android 4.1.1; M040 Build/JRO03H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/31.0.1650.59 Mobile Safari/537.36",
//            		"Meizu MX2 M040  Android 4.1     猎豹 2.8  Mozilla/5.0 (Linux; Android 4.1.1; M040 Build/JRO03H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/28.0.1500.64 Mobile Safari/537.36",
//            		"Meizu MX2 M040  Android 4.1     Baidu 4.1   Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/534.24 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.24 T5/2.0 baidubrowser/4.2.4.0 (Baidu; P1 4.1.1)",
//            		"Meizu MX M031   Android 4.1     -built-in *     Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M031 Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Meizu MX M031   Android 4.1     UC 8.8  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-CN; M031 Build/JRO03H) AppleWebKit/534.31 (KHTML, like Gecko) UCBrowser/8.8.3.278 U3/0.8.0 Mobile Safari/534.31",
//            		"Meizu MX2 M040  Android 4.1     QQ 4.1  Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/533.1 (KHTML, like Gecko)Version/4.0 MQQBrowser/4.1 Mobile Safari/533.1",
//            		"Meizu MX2 M040  Android 4.1     -built-in *     Mozilla/5.0 (Linux; U; Android 4.1.1; zh-cn; M040 Build/JRO03H) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//            		"Samsung P6200(GALAXY Tab)   Android 3.2     QQ HD 2.1   Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3; en-us) AppleWebKit/533.16 (KHTML, like Gecko) Version/5.0 Safari/533.16",
//            		"Samsung P6200(GALAXY Tab)   Android 3.2     UC HD 2.3   Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_6_3) AppleWebKit/534.31 (KHTML, like Gecko) Chrome/17.0.558.0 Safari/534.31 UCBrowser/2.3.1.257"};
//            conn.setRequestProperty("User-Agent", ug[(int)(Math.random()*1000) % ug.length]);            	
//            
//            BufferedInputStream in;
//            try{ 
//            	in = new BufferedInputStream(conn.getInputStream());  
//            }catch(IOException e){
//            	return null;
//            }
//              
//            try{  
//                String inputLine;  
//                byte[] buf = new byte[4096];  
//                int bytesRead = 0;  
//                while (bytesRead >= 0) {  
//                    inputLine = new String(buf, 0, bytesRead, "UTF-8");  
//                    html.append(inputLine);  
//                    bytesRead = in.read(buf);  
//                    inputLine = null;  
//                }  
//                buf = null;  
//            }finally{  
//                in.close();  
//                conn = null;  
//                url = null;  
//            }  
//            result = html.toString().trim();  
//              
//        }catch (Exception e) {  
//            e.printStackTrace();  
//            return null;  
//        }finally{  
//            html = null;              
//        }  
//        return result; 
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