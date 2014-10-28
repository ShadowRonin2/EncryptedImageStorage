import java.io.File;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.CookieStore;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.cookie.Cookie;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.ContentBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;



public class upLoadFileTest {
	/**
	 * A simple example of uploading a file to the apache server. 
	 */
	public static void main(String[] args) throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    CookieStore cookieStore = new BasicCookieStore();
	    HttpContext httpContext = new BasicHttpContext();
	    httpContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
	    
	    //login
	    //HttpPost loginPost = new HttpPost("http://" + ip + "/login/includes/process_login.php");
	    HttpPost loginPost = new HttpPost("http://" + ip + "/login/login.php");
	    MultipartEntityBuilder loginBuilder = MultipartEntityBuilder.create();
	    loginBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
	    ContentBody email = new StringBody("test@example.com");
	    
	    String password = "6ZaxN2Vzm9NUJT2y";
	    String salt = "f9aab579fc1b41ed0c44fe4ecdbfcdb4cb99b9023abb241a6db833288f4eea3c02f76e0d35204a8695077dcf81932aa59006423976224be0390395bae152d4ef";
	    byte[] hash = new byte[0];
	    try {
	        MessageDigest digest = MessageDigest.getInstance("SHA-256");

	        hash =  digest.digest((password + salt).getBytes("UTF-8"));
	    } catch (UnsupportedEncodingException | NoSuchAlgorithmException e) {
	        System.out.println("WE FAILED BOYS");
	    }
	    System.out.println(hash.toString());
	    ContentBody hashedPass = new StringBody(password);
	    
	    loginBuilder.addPart("email", email);
	    loginBuilder.addPart("password", hashedPass);
	    HttpEntity lgEn = loginBuilder.build();
	    loginPost.setEntity(lgEn);
	    
	    System.out.println("executing request " + loginPost.getRequestLine());
	    HttpResponse response2 = httpclient.execute(loginPost, httpContext);
	    HttpEntity reEntity = response2.getEntity();
	    System.out.println(response2.getStatusLine());
	    if (reEntity != null) {
	      //System.out.println(EntityUtils.toString(reEntity));
	    }
	    //System.out.println(httpContext.toString());
	    for(Cookie cookie : cookieStore.getCookies()) {
	    	System.out.println(cookie.getName());
	    	System.out.println(cookie.getValue());
	    }
	    
	    
	    //upload file
	    HttpPost httppost = new HttpPost("http://"+ip+"/upload.php");
	    File file = new File("sample.jpeg");
	    
	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
	    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

	    builder.addBinaryBody("userfile", file, ContentType.APPLICATION_OCTET_STREAM, "sample.jpeg");
	    HttpEntity mpEntity = builder.build();

	    httppost.setEntity(mpEntity);
	    System.out.println("executing request " + httppost.getRequestLine());
	    HttpResponse response = httpclient.execute(httppost, httpContext);
	    HttpEntity resEntity = response.getEntity();

	    System.out.println(response.getStatusLine());
	    if (resEntity != null) {
	      System.out.println(EntityUtils.toString(resEntity));
	    }
	    httpclient.close();
	  }
	}