
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**

 @author Kody
 */
public class HTTPRequest extends Thread
{

   private static String CRLF = "\r\n";

   private Socket sock;
   private PrintWriter writeSock;
   private BufferedReader readSock;
   private HTTP http;

   public HTTPRequest(Socket sock)
   {
      this.sock = sock;
      try
      {
         writeSock = new PrintWriter(sock.getOutputStream(), true);
         readSock = new BufferedReader(new InputStreamReader(
               sock.getInputStream()));
      }
      catch (Exception e)
      {
         System.out.println("Error : " + e);
      }
   }

   @Override
   public void start()
   {
      try {
      http = new HTTP(readSock.readLine());
      writeSock.print(fetchStatus());
      System.out.println(fetchStatus());
      writeSock.print(fetchHeader());
      System.out.println(fetchHeader());
      writeSock.println(fetchBody());
      
         sock.close();
      } catch (Exception e) {
         
      }
      
   }

   private String fetchStatus()
   {
      if (http.isValid())
      {
         return "HTTP/1.0 200 OK" + CRLF;
      }
      else if (!http.isValidMethod())
      {
         return "HTTP/1.0 400 Bad Request" + CRLF;
      }
      else if (!http.isValidFile())
      {
         return "HTTP/1.0 404 Not Found" + CRLF;
      }
      else if (!http.isValidVersion())
      {
         return "HTTP/1.0 505 HTTP Version Not Supported" + CRLF;
      }
      return "HTTP/1.0 400 Bad Request" + CRLF;
   }

   private String fetchHeader()
   {
      return "Content-type: " + contentType(http.getUrl() + CRLF);
   }

   private String fetchBody()
   {
      return "<HTML>" + "<HEAD><TITLE>YES</TITLE></HEAD>"
+ "<BODY>YES</BODY></HTML>";
   }

   private String contentType(String fileName)
   {
      if (fileName.endsWith(".htm") || fileName.endsWith(".html"))
      {
         return "text/html";
      }
      else if (fileName.endsWith(".gif"))
      {
         return "image/gif";
      }
      else if (fileName.endsWith(".jpeg") || fileName.endsWith(".jpg"))
      {
         return "image/jpeg";
      }
      else if (fileName.endsWith(".jpeg"))
      {
         return "image/jpeg";
      }
      else
      {
         return "text/html";
      }
   }

}
