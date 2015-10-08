
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.StringTokenizer;

/**

 @author Kody
 */
public class HTTP
{

   private static final int CHUNK_SIZE = 1024;
   
   private String method;
   private String url;
   
   private Socket sock;
   private FileInputStream fis;
   private OutputStream os;

   public HTTP(String request)
   {
      parseRequest(request);
      System.out.println("Method : " + method);
      System.out.println("Url : " + url);
   }
   
   public boolean isFileValid() {
      try{
         fis = new FileInputStream(url);
      } catch (FileNotFoundException fe) {
         return false;
      }
      return true;
   }
   
   public String getContentType() {
      if(url.endsWith(".html") || url.endsWith(".htm") ) {
         return "text/html";
      }
      if(url.endsWith(".gif")) {
         return "image/gif";
      }
      if(url.endsWith(".jpg") || url.endsWith(".jpeg") ) {
         return "image/jpeg";
      }
      if(url.endsWith(".bmp")) {
         return "image/bmp";
      }
      return "application/octet-stream";
   }
   
   public boolean sendBody(Socket sock) {
      this.sock = sock;
      byte[] buffer = new byte[CHUNK_SIZE];
      buffer = fis.read(buffer);
      
      
      
      return true;
   }
   
   private void parseRequest(String request) {
      StringTokenizer st = new StringTokenizer(request);
      if(st.hasMoreTokens()) {
         this.method = st.nextToken();
      }
      if(st.hasMoreTokens()) {
         this.url = "." + st.nextToken();
      }
   }

}
