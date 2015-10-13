
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.Socket;
import java.util.StringTokenizer;

/**
 Manages and contains the http request information

 @author Kody
 */
public class HTTP
{

   private static final int CHUNK_SIZE = 1024;

   private String url;
   private boolean fileFound = true;

   private FileInputStream fis;
   private DataOutputStream os;

   /**
    Constructor that takes in the raw request

    @param request
    */
   public HTTP(String request)
   {
      parseRequest(request);
   }

   /**
    Checks to see if the file is valid

    @return true is valid, else false
    */
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

   /**
    Gets the types of content the request is asking for

    @return Content Type string
    */
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

   /**
    Sends the content of the body through the socket

    @param sock socket to send through
    @return if socket was sent successfully, else false
    */
   public boolean sendBody(Socket sock)
   {
      try
      {
         os = new DataOutputStream(sock.getOutputStream());
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
            return false;
         }
      }
      catch (Exception e)
      {
         System.out.println("HTTP Exception : " + e);
      }
      return true;
   }

   /**
    Parses out the response

    @param request the raw request string
    */
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
