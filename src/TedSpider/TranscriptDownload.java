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
			TranscriptDownload.downloadUnit(urlArray, languageType, folderName, start, end);
		} catch (Exception e) {
			System.err.println(threadName + " interrupted.");
		}
		System.out.println(threadName + " exiting.");
	}

	public void start() {
		System.out.println(threadName + "Starting " + "total" + urlArray.length + " NO." + start + "~" + "NO." + end);
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
	public static void download(String url, String languageType, String folderName) throws Exception {
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

	/**
	 * 保存文件
	 * 
	 * @param fileName
	 * @param scriptList
	 * @param timeList
	 * @param code
	 * @throws Exception
	 */
	public static void writeFile(String fileName, ArrayList<String> scriptList, ArrayList<String> timeList, String code)
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
	public static void downloadUnit(String[] urlArray, String languageType, String folderName, int start, int end)
			throws Exception {
		for (int i = start; i <= end; i++) {
			System.err.println("NO." + i + "   " + urlArray[i]);
			try {
				download(urlArray[i], languageType, folderName);
			} catch (NullPointerException ex) {
				continue;
			}
		}
	}

	public static void multiDownload(String language, String folderPath, int threadNum) throws Exception {
		UrlMatch um = new UrlMatch();
		um.matchPageUrl("https://www.ted.com/talks?language=zh-cn", 1, 3);
		UrlMatch umOther = new UrlMatch();
		umOther.matchPageUrl("https://www.ted.com/talks?language=" + language, 1, 3);
		System.out.println("含有中文文本的演讲个数：" + um.urlSet.size());
		System.out.println("含有外文文本的演讲个数：" + umOther.urlSet.size());
		um.urlSet.retainAll(umOther.urlSet);
		System.out.println("交集后,演讲个数：" + um.urlSet.size());
		String[] urlArray = um.urlSet.toArray(new String[um.urlSet.size()]);
		
		System.out.println("开始进行多进程爬取");
		int sumNum = urlArray.length;
		System.out.println(sumNum);
		for (int i = 0; i < threadNum; i++) {
			DownloadThread t = new DownloadThread("Thread-" + (i + 1), urlArray, language, folderPath,
					sumNum / threadNum * i + 1, sumNum / threadNum * (i + 1));
			t.start();
		}

	}

	public static void main(String args[]) throws Exception {
		multiDownload("vi", "transcript/test2", 3);

	}
}
