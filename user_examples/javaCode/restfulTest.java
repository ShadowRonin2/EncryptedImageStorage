import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class restfulTest {
	
	public static void main(String[] args) throws Exception{
		hello();
		postFile();
	}
	
	public static void hello() throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    
	    //login
	    HttpPost loginPost = new HttpPost("http://" + ip + "/restful/get/hello.json");
	    MultipartEntityBuilder loginBuilder = MultipartEntityBuilder.create();
	    loginBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody user = new StringBody("foo");
	    ContentBody pass = new StringBody("bar");
	    
	    loginBuilder.addPart("username", user);
	    loginBuilder.addPart("password", pass);
	    HttpEntity lgEn = loginBuilder.build();
	    loginPost.setEntity(lgEn);
	    
	    System.out.println("executing request " + loginPost.getRequestLine());
	    HttpResponse response2 = httpclient.execute(loginPost);
	    HttpEntity reEntity = response2.getEntity();
	    System.out.println(response2.getStatusLine());
	    if (reEntity != null) {
	      System.out.println(EntityUtils.toString(reEntity));
	    }
	    httpclient.close();
	}
	 
	public static void postFile() throws Exception{
		CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    //upload file
	    HttpPost httppost = new HttpPost("http://"+ip+"/post/file.xml");
	    File file = new File("sample.jpeg");
	    
	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
	    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    builder.addTextBody("username", "foo");
	    builder.addTextBody("password", "bar");
	    builder.addBinaryBody("userfile", file, ContentType.APPLICATION_OCTET_STREAM, "sample.jpeg");
	    
	    HttpEntity mpEntity = builder.build();
	    
	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost);
	    HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      System.out.println(EntityUtils.toString(resEntity));
	    }
	    
	    httpclient.close();
	  }
	}