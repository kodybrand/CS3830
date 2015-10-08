
import java.net.ServerSocket;
import java.net.Socket;

/**

 @author Kody
 */
public class WebServer
{

   private static final int PORT = 5764;

   public WebServer()
   {

   }

   public void run()
   {
      try
      {
         ServerSocket servSock = new ServerSocket(PORT);
         System.out.println("Server is running... Waiting for requests...");
         while (true)
         {
            Socket sock = servSock.accept();
            HTTPRequest request = new HTTPRequest(sock);
            System.out.println("Connection from " + sock.getInetAddress() 
                  + " on port " + sock.getPort());
            request.start(); 
         }
      }
      catch (Exception e)
      {
         System.out.println("Socket Exception : " + e);
      }
   }
}
