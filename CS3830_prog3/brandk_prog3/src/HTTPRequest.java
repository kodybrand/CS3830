
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
   private static String VERSION = "HTTP/1.0 ";

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
         System.out.println("HTTPRequest Exception : " + e);
      }
   }

   @Override
   public void start()
   {
      try
      {
         http = new HTTP(readSock.readLine());
         writeSock.print(buildStatus());
         writeSock.print(buildHeader());
         sendBody();
         sock.close();
      }
      catch (Exception e)
      {
         System.out.println("HTTPRequest Exception : " + e);
      }

   }

   private String buildStatus()
   {
      String response = VERSION;
      response = response + getStatus() + CRLF;

      return response;
   }

   private String getStatus()
   {
      if (http.isFileValid())
      {
         return "200 OK ";
      }
      else
      {
         return "404 Not Found ";
      }
   }

   private String buildHeader()
   {
      return "Content-type: " + http.getContentType();
   }

   private void sendBody()
   {
      http.sendBody(sock);
   }
}
