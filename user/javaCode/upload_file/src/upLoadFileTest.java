import java.io.File;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;



public class upLoadFileTest {
	/**
	 * A simple example of uploading a file to the apache server. 
	 */
	public static void main(String[] args) throws Exception {
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    //change ip to what your host ip is
	    String ip = "127.0.0.1:8080";
	    HttpPost httppost = new HttpPost("http://"+ip+"/upload.php");
	    File file = new File("sample.jpeg");

	    MultipartEntityBuilder builder = MultipartEntityBuilder.create();        
	    builder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

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