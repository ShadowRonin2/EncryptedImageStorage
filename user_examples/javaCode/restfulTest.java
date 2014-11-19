import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Scanner;

import org.apache.commons.codec.binary.Base64;
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
		//hello();
		postFile();
		getFile();
		//delF();
		//putPassword();
		
	}
	
	public static void hello() throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    
	    //login
	    HttpPost loginPost = new HttpPost("http://" + ip + "/get/hello.json");
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
	
	public static void delF() throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    
	    //login
	    HttpPost loginPost = new HttpPost("http://" + ip + "/restful/delete/file.json");
	    MultipartEntityBuilder loginBuilder = MultipartEntityBuilder.create();
	    loginBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody user = new StringBody("foo");
	    ContentBody pass = new StringBody("bar");
	    ContentBody file = new StringBody("sample.jpeg");
	    
	    loginBuilder.addPart("username", user);
	    loginBuilder.addPart("password", pass);
	    loginBuilder.addPart("filename", file);
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
	    HttpPost httppost = new HttpPost("http://"+ip+"/put/file.xml");
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
	
	public static void getFile() throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    String out = "";
	    
	    //login
	    HttpPost loginPost = new HttpPost("http://" + ip + "/get/file.xml");
	    MultipartEntityBuilder loginBuilder = MultipartEntityBuilder.create();
	    loginBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody user = new StringBody("foo");
	    ContentBody pass = new StringBody("bar");
	    ContentBody file = new StringBody("sample.jpeg");
	    
	    loginBuilder.addPart("username", user);
	    loginBuilder.addPart("password", pass);
	    loginBuilder.addPart("filename", file);
	    
	    HttpEntity lgEn = loginBuilder.build();
	    loginPost.setEntity(lgEn);
	    
	    System.out.println("executing request " + loginPost.getRequestLine());
	    HttpResponse response2 = httpclient.execute(loginPost);
	    HttpEntity reEntity = response2.getEntity();
	    System.out.println(response2.getStatusLine());
	    if (reEntity != null) {
	      out = EntityUtils.toString(reEntity);
	      System.out.println(out);
	    }
	  
	    System.out.println();
	    Scanner in = new Scanner(out);
	    in.nextLine();
	    in.nextLine();
	    in.nextLine();
	    String line = in.nextLine();
	    line = line.substring(7, line.length()-7);
	    System.out.println(line);
	    byte[] decoded = Base64.decodeBase64(line);
	    OutputStream fileOut = new BufferedOutputStream(new FileOutputStream("/home/shadowx/Desktop/test.jpeg"));
	    fileOut.write(decoded);
	    
	    httpclient.close();
	}

	public static void putPassword() throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    
	    //login
	    HttpPost loginPost = new HttpPost("http://" + ip + "/restful/put/password.json");
	    MultipartEntityBuilder loginBuilder = MultipartEntityBuilder.create();
	    loginBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody user = new StringBody("foo");
	    ContentBody pass = new StringBody("bar");
	    ContentBody newPassword = new StringBody("testytesttest");
	    
	    loginBuilder.addPart("username", user);
	    loginBuilder.addPart("password", pass);
	    loginBuilder.addPart("newPassword", newPassword);
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
}