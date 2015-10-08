
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

   private String url;
   private boolean fileFound = true;

   private FileInputStream fis;
   private OutputStream os;

   public HTTP(String request)
   {
      parseRequest(request);
   }

   public boolean isFileValid()
   {
      try
      {
         fis = new FileInputStream(url);
      }
      catch (FileNotFoundException fe)
      {
         
         fileFound = false;
         return false;
      }
      return true;
   }

   public String getContentType()
   {
      if (url.endsWith(".html") || url.endsWith(".htm"))
      {
         return "text/html";
      }
      if (url.endsWith(".gif"))
      {
         return "image/gif";
      }
      if (url.endsWith(".jpg") || url.endsWith(".jpeg"))
      {
         return "image/jpeg";
      }
      if (url.endsWith(".bmp"))
      {
         return "image/bmp";
      }
      return "application/octet-stream";
   }

   public boolean sendBody(Socket sock)
   {
      try
      {
         os = sock.getOutputStream();
         byte[] buffer = new byte[CHUNK_SIZE];
         int bytes = 0;
         if (fileFound)
         {
            
            while ((bytes = fis.read(buffer)) != -1)
            {
               os.write(buffer, 0, bytes);
            }
            System.out.println("File sent : " + url);
         }
         else
         {
            String entityBody = "<HTML>"
                  + "<HEAD><TITLE>Not Found</TITLE></HEAD>"
                  + "<BODY>Not Found</BODY></HTML>";
            byte[] b = entityBody.getBytes();
            os.write(b);
            System.out.println("No file sent");
         }
      }
      catch (Exception e)
      {
         System.out.println("HTTP Exception : " + e);
      }
      return true;
   }

   private void parseRequest(String request)
   {
      StringTokenizer st = new StringTokenizer(request);
      if (st.hasMoreTokens())
      {
         st.nextToken();
      }
      if (st.hasMoreTokens())
      {
         this.url = "." + st.nextToken();
      }
   }

}
