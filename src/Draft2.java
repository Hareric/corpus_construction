import java.io.IOException;
import java.lang.reflect.Proxy;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.HttpURLConnection;  


public class Draft2 {
	public static void main(String[] args) throws MalformedURLException, IOException{
		System.getProperties().setProperty("proxySet", "true"); 	
	    System.getProperties().setProperty("http.proxyHost", "218.26.204.66");
	    System.getProperties().setProperty("http.proxyPort", "8080");

	    HttpURLConnection connection = (HttpURLConnection) new URL("http://www.baidu.com/").openConnection();
		connection.setConnectTimeout(6000); // 6s
		connection.setReadTimeout(6000);
		connection.setUseCaches(false);

	    if(connection.getResponseCode() == 200){
	    	System.out.println("使用代理IP连接网络成功");
	    	System.out.println(connection.getContent());
	    }
	}
}
