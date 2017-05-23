package TedSpider;

import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.regex.Matcher;

class DownloadThread extends Thread {

	private Thread t;
	private String threadName;
	private String[] urlArray;
	private String languageType;
	private String folderName;
	private int start;
	private int end;

	public DownloadThread(String threadName, String[] urlArray, String languageType, String folderName, int start,
			int end) {
		this.threadName = threadName;
		this.urlArray = urlArray;
		this.languageType = languageType;
		this.folderName = folderName;
		this.start = start;
		this.end = end;
	}

	public void run() {
		try {
			new TranscriptDownload().downloadUnit(urlArray, languageType, folderName, start, end);
		} catch (Exception e) {
			System.err.println(threadName + " interrupted.");
		}
		System.out.println(threadName + " exiting.");
	}

	public void start() {
		System.out
				.println(threadName + "  Starting " + "total:" + urlArray.length + " NO." + start + "~" + "NO." + end);
		if (t == null) {
			t = new Thread(this, threadName);
			t.start();
		}
	}
}

public class TranscriptDownload {
	/**
	 * 爬取演讲稿的中文文本和对应的外文文本 并对应保存成txt
	 * 
	 * @param url
	 * @param languageType
	 * @param folderName
	 * @throws Exception
	 */
	public void download(String url, String languageType, String folderName) throws Exception {
		String htmlsc = spider.Crawl.getPage(url);
		TranscriptMatch tsmZh = new TranscriptMatch();
		htmlsc = spider.Crawl.getPage(url + "?language=zh-cn");

		tsmZh.match(htmlsc);
		String htmlscl = "";
		TranscriptMatch tsm = new TranscriptMatch();
		try {
			htmlscl = spider.Crawl.getPage(url + "?language=" + languageType);
		} catch (Exception e) {
			System.err.println("无外文文本");
			return;
		}
		tsm.match(htmlscl);
		try {
			tsmZh.addScript(tsm.getScriptList(), tsm.getTimeList());
		} catch (Exception e) {
			System.err.println("时间轴无法匹配 舍去");
			return;
		}

		Matcher titleMatch = TranscriptMatch.regexString(url, "talks/(.*?)/transcript");
		String title = "";
		if (titleMatch.find()) {
			title = titleMatch.group(1);
		}
		writeFile(folderName + "/" + title + ".txt", tsmZh.getScriptList(), tsmZh.getTimeList(), "UTF-8");

	}

	public void switchIp(double r) {
		String IpPort[][] = { { "175.155.241.114", "808" }, { "183.153.15.153", "808" }, { "119.5.1.62", "808" },
				{ "58.209.151.126", "808" }, { "180.76.154.5", "8888" }, { "1.192.247.78", "8118" },
				{ "220.166.96.90", "82" }, { "115.220.1.134", "808" }, { "116.226.90.12", "808" },
				{ "139.224.237.33", "8888" } };
		int rr = (int) (r * 100) % IpPort.length;
//		System.out.println(IpPort[rr][0] + ":" + IpPort[rr][1]);
		System.getProperties().setProperty("proxySet", "true");
		System.getProperties().setProperty("http.proxyHost", IpPort[rr][0]);
		System.getProperties().setProperty("http.proxyPort", IpPort[rr][1]);

	}

	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param scriptList
	 * @param timeList
	 * @param code
	 * @throws Exception
	 */
	public void writeFile(String fileName, ArrayList<String> scriptList, ArrayList<String> timeList, String code)
			throws Exception {
		RandomAccessFile raf = new RandomAccessFile(fileName, "rw");
		raf.seek(raf.length());
		for (int i = 0; i < scriptList.size(); i++) {
			raf.write(timeList.get(i).getBytes(code));
			raf.write("\n".getBytes());
			raf.write(scriptList.get(i).getBytes(code));
			raf.write("\n".getBytes(code));
		}
		raf.close();
		System.err.println("写入完成");
	}

	/**
	 * 单个线程运行函数
	 * 
	 * @param urlArray
	 * @param languageType
	 * @param folderName
	 * @throws Exception
	 */
	public void downloadUnit(String[] urlArray, String languageType, String folderName, int start, int end)
			throws Exception {
		for (int i = start; i <= end; i++) {
			System.err.println("NO." + i + "   " + urlArray[i]);
			try {
				download(urlArray[i], languageType, folderName);
			} catch (NullPointerException ex) {
				i--;
				System.out.println("网页获取失败 该线程暂停");
				this.switchIp(Math.random());
				Thread.sleep((int)(Math.random()*10000));
				continue;
			}
		}
	}

	public void multiDownload(String language, String folderPath, int threadNum) throws Exception {
		UrlMatch um = new UrlMatch();
		System.out.println("获取演讲标题...");
		um.matchPageUrl("https://www.ted.com/talks?language=zh-cn");
		UrlMatch umOther = new UrlMatch();
		umOther.matchPageUrl("https://www.ted.com/talks?language=" + language);
		System.out.println("含有中文文本的演讲个数：" + um.urlSet.size());
		System.out.println("含有外文文本的演讲个数：" + umOther.urlSet.size());
		um.urlSet.retainAll(umOther.urlSet);
		System.out.println("交集后,演讲个数：" + um.urlSet.size());
		Thread.sleep(1500);
		String[] urlArray = um.urlSet.toArray(new String[um.urlSet.size()]);

		System.out.println("\n\n开始进行多进程爬取");
		int sumNum = urlArray.length;
		for (int i = 0; i < threadNum; i++) {
			DownloadThread t = new DownloadThread("Thread-" + (i + 1), urlArray, language, folderPath,
					sumNum / threadNum * i + 1, sumNum / threadNum * (i + 1));
			t.start();
			Thread.sleep((int)(Math.random()*10000));
			
			
		}

	}

	public static void main(String args[]) throws Exception {
		new TranscriptDownload().multiDownload("vi", "transcript/vi", 2);  // (id|ms|vi, 保存文件夹, 线程数)
	}
}
